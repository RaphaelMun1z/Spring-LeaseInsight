package com.rm.leaseinsight.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.leaseinsight.entities.Contract;
import com.rm.leaseinsight.entities.RentalHistory;
import com.rm.leaseinsight.entities.Residence;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.entities.enums.PaymentStatus;
import com.rm.leaseinsight.repositories.ContractRepository;
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
	public List<Contract> findAllCached() {
		return findAll();
	}

	public List<Contract> findAll() {
		return repository.findAll();
	}

	public Contract findById(String id) {
		Optional<Contract> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Contract", id));
	}

	@Transactional
	public Contract create(Contract obj) {
		try {
			Residence r = residenceService.findById(obj.getResidence().getId());
			if (r.getActiveContract() != null) {
				throw new DataViolationException("Residence already has a Contract");
			}
			
			obj.setResidence(r);
			Tenant t = tenantService.findById(obj.getTenant().getId());
			obj.setTenant(t);
			Contract contract = repository.save(obj);
			cacheService.putContractCache();
			contractAsyncService.sendContractBeginEmail(contract);
			createFirstRental(contract);
			return contract;
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		} catch (DataViolationException e) {
			throw e;
		}
	}

	private void createFirstRental(Contract c) {
		try {
			RentalHistory rental = new RentalHistory(null, c.getContractStartDate(), PaymentStatus.PENDING, c);
			rentalHistoryService.create(rental);
			contractAsyncService.sendInvoiceByEmail(c, rental);
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
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

	public Set<Contract> findByContractStatus(Integer code) {
		return repository.findByContractStatus(code);
	}

	public Set<Contract> findByTenant(String id) {
		Tenant tenant = tenantService.findById(id);
		return repository.findByTenant(tenant);
	}
}
