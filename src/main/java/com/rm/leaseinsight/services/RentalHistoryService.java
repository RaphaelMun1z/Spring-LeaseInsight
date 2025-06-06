package com.rm.leaseinsight.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.leaseinsight.dto.req.RentalHistoryRequestDTO;
import com.rm.leaseinsight.dto.res.RentalHistoryMinimalResponseDTO;
import com.rm.leaseinsight.dto.res.RentalHistoryResponseDTO;
import com.rm.leaseinsight.entities.Contract;
import com.rm.leaseinsight.entities.RentalHistory;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.mapper.Mapper;
import com.rm.leaseinsight.repositories.ContractRepository;
import com.rm.leaseinsight.repositories.RentalHistoryRepository;
import com.rm.leaseinsight.resources.RentalHistoryResource;
import com.rm.leaseinsight.services.exceptions.DataViolationException;
import com.rm.leaseinsight.services.exceptions.DatabaseException;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class RentalHistoryService {
	@Autowired
	private RentalHistoryRepository repository;

	@Autowired
	@Lazy
	private ContractService contractService;

	@Autowired
	private TenantService tenantService;

	@Autowired
	private ContractRepository contractRepository;

	@Autowired
	private CacheService cacheService;

	@Cacheable("findAllRentalHistory")
	public List<RentalHistoryResponseDTO> findAllCached() {
		return findAll();
	}

	public List<RentalHistoryResponseDTO> findAll() {
		List<RentalHistory> rentalHistories = repository.findAll();
		List<RentalHistoryResponseDTO> rentalHistoriesResponse = rentalHistories.stream()
				.map(rentalHistory -> new RentalHistoryResponseDTO(rentalHistory)).collect(Collectors.toList());

		rentalHistoriesResponse.forEach(rentalHistory -> rentalHistory
				.add(linkTo(methodOn(RentalHistoryResource.class).findById(rentalHistory.getId())).withSelfRel()));
		return rentalHistoriesResponse;
	}

	public RentalHistory findById(String id) {
		Optional<RentalHistory> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Rental History", id));
	}

	public List<RentalHistoryMinimalResponseDTO> findAllMinimal() {
		List<RentalHistoryResponseDTO> rentalHistories = this.findAll();
		List<RentalHistoryMinimalResponseDTO> rentalHistoriesMinimalResponse = rentalHistories.stream()
				.map(rentalHistoryMinimal -> new RentalHistoryMinimalResponseDTO(rentalHistoryMinimal))
				.collect(Collectors.toList());

		rentalHistoriesMinimalResponse.forEach(rentalHistoryMinimal -> rentalHistoryMinimal.add(
				linkTo(methodOn(RentalHistoryResource.class).findById(rentalHistoryMinimal.getId())).withSelfRel()));

		return rentalHistoriesMinimalResponse;
	}

	@Transactional
	public RentalHistoryResponseDTO create(RentalHistoryRequestDTO obj) {
		try {
			Contract contract = contractRepository.findById(obj.getContractId())
					.orElseThrow(() -> new ResourceNotFoundException("Contract", obj.getContractId()));
			RentalHistory rentalHistory = new RentalHistory(null, obj.getRentalStartDate(), obj.getPaymentStatus(),
					contract);
			RentalHistory rentalHistorySaved = repository.save(rentalHistory);

			RentalHistoryResponseDTO rentalHistoryDTO = Mapper.modelMapper(rentalHistorySaved,
					RentalHistoryResponseDTO.class);
			rentalHistoryDTO.add(
					linkTo(methodOn(RentalHistoryResource.class).findById(rentalHistorySaved.getId())).withSelfRel());
			cacheService.putRentalHistoryCache();

			return rentalHistoryDTO;
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException("Rental History");
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public void delete(String id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				cacheService.evictAllCacheValues("findAllRentalHistory");
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
	public RentalHistory patch(String id, RentalHistory obj) {
		try {
			RentalHistory entity = repository.getReferenceById(id);
			patchData(entity, obj);
			RentalHistory rh = repository.save(entity);
			cacheService.putRentalHistoryCache();
			return rh;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void patchData(RentalHistory entity, RentalHistory obj) {
		if (obj.getPaymentStatus() != null)
			entity.setPaymentStatus(obj.getPaymentStatus());
	}

	@PreAuthorize("@authenticatedUserService.hasId(#id)")
	public Set<RentalHistory> findByTenant(String id) {
		try {
			Tenant tenant = tenantService.findById(id);
			return repository.findByContractTenant(tenant);
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
