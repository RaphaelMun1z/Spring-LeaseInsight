package com.rm.leaseinsight.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.leaseinsight.dto.req.ContractRequestDTO;
import com.rm.leaseinsight.dto.req.RentalHistoryRequestDTO;
import com.rm.leaseinsight.dto.res.ContractResponseDTO;
import com.rm.leaseinsight.entities.Contract;
import com.rm.leaseinsight.entities.RentalHistory;
import com.rm.leaseinsight.entities.Residence;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.entities.enums.OccupancyStatus;
import com.rm.leaseinsight.entities.enums.PaymentStatus;
import com.rm.leaseinsight.mapper.Mapper;
import com.rm.leaseinsight.repositories.ContractRepository;
import com.rm.leaseinsight.resources.ContractResource;
import com.rm.leaseinsight.services.async.ContractAsyncService;
import com.rm.leaseinsight.services.exceptions.DataViolationException;
import com.rm.leaseinsight.services.exceptions.DatabaseException;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class ContractService {
	@Autowired
	private ContractRepository repository;

	@Autowired
	private ResidenceService residenceService;

	@Autowired
	private TenantService tenantService;

	@Autowired
	private RentalHistoryService rentalHistoryService;

	@Autowired
	private ContractAsyncService contractAsyncService;

	@Autowired
	private CacheService cacheService;

	@Cacheable("findAllContract")
	public List<ContractResponseDTO> findAllCached() {
		return findAll();
	}

	public List<ContractResponseDTO> findAll() {
		List<Contract> contracts = repository.findAll();
		List<ContractResponseDTO> contractsResponse = contracts.stream()
				.map(contract -> new ContractResponseDTO(contract)).collect(Collectors.toList());

		contractsResponse.forEach(contract -> contract
				.add(linkTo(methodOn(ContractResource.class).findById(contract.getId())).withSelfRel()));
		return contractsResponse;
	}

	public ContractResponseDTO findById(String id) {
		Contract contract = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contract", id));
		ContractResponseDTO contractDTO = new ContractResponseDTO(contract);
		contractDTO.add(linkTo(methodOn(ContractResource.class).findById(id)).withSelfRel());
		return contractDTO;
	}

	@Transactional
	public ContractResponseDTO create(ContractRequestDTO obj) {
		try {
			String residenceId = obj.getResidenceId();
			Residence r = residenceService.findById(residenceId);

			if (r.getOccupancyStatus() != OccupancyStatus.AVAILABLE) {
				throw new DataViolationException("Residence is not available");
			}

			if (r.getActiveContract() != null) {
				throw new DataViolationException("Residence already has a Contract");
			}

			Contract c = new Contract(null, null, null, obj.getContractStartDate(), obj.getContractEndDate(),
					obj.getDefaultRentalValue(), obj.getContractStatus(), obj.getInvoiceDueDate());

			c.setResidence(r);

			String tenantId = obj.getTenantId();
			Tenant t = tenantService.findById(tenantId);
			c.setTenant(t);

			repository.save(c);

			cacheService.putContractCache();
			contractAsyncService.sendContractBeginEmail(c);

			createFirstRental(c);

			ContractResponseDTO cDTO = new ContractResponseDTO(c);
			return cDTO;
		} catch (DataIntegrityViolationException | InvalidDataAccessApiUsageException e) {
			throw new DataViolationException("Data access error", e);
		}
	}

	private void createFirstRental(Contract c) {
		try {
			RentalHistory rental = new RentalHistory(null, c.getContractStartDate(), PaymentStatus.PENDING, c);
			rentalHistoryService.create(new RentalHistoryRequestDTO(rental));
			contractAsyncService.sendInvoiceByEmail(c, rental);
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	public Set<Contract> findByContractStatus(Integer code) {
		return repository.findByContractStatus(code);
	}

	@PreAuthorize("@authenticatedUserService.hasId(#id)")
	public Set<ContractResponseDTO> findByTenant(String id) {
		Tenant tenant = tenantService.findById(id);
		Set<Contract> contracts = repository.findByTenant(tenant);
		Set<ContractResponseDTO> contractsResponse = contracts.stream()
				.map(contract -> Mapper.modelMapper(contract, ContractResponseDTO.class)).collect(Collectors.toSet());
		contractsResponse.forEach(contract -> contract
				.add(linkTo(methodOn(ContractResource.class).findById(contract.getId())).withSelfRel()));
		return contractsResponse;
	}

	@Transactional
	public void delete(String id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				cacheService.evictAllCacheValues("findAllContract");
			} else {
				throw new ResourceNotFoundException(id);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Transactional
	public Contract patch(String id, Contract obj) {
		try {
			Contract entity = repository.getReferenceById(id);
			patchData(entity, obj);
			Contract c = repository.save(entity);
			cacheService.putContractCache();
			return c;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void patchData(Contract entity, Contract obj) {
		if (obj.getContractStartDate() != null)
			entity.setContractStartDate(obj.getContractStartDate());
		if (obj.getContractEndDate() != null)
			entity.setContractEndDate(obj.getContractEndDate());
		if (obj.getDefaultRentalValue() != null)
			entity.setDefaultRentalValue(obj.getDefaultRentalValue());
		if (obj.getContractStatus() != null)
			entity.setContractStatus(obj.getContractStatus());
		if (obj.getInvoiceDueDate() != 0)
			entity.setInvoiceDueDate(obj.getInvoiceDueDate());
	}
}
