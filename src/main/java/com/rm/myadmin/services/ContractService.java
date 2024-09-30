package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.myadmin.entities.Contract;
import com.rm.myadmin.entities.RentalHistory;
import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.entities.Tenant;
import com.rm.myadmin.entities.enums.PaymentStatus;
import com.rm.myadmin.repositories.ContractRepository;
import com.rm.myadmin.services.async.ContractAsyncService;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

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

	public Contract findById(Long id) {
		Optional<Contract> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public Contract create(Contract obj) {
		try {
			Residence r = residenceService.findById(obj.getResidence().getId());
			obj.setResidence(r);
			r.setContract(obj);
			obj.setTenant(tenantService.findById(obj.getTenant().getId()));
			Contract contract = repository.save(obj);
			contractAsyncService.sendContractBeginEmail(contract);
			createFirstRental(contract);
			cacheService.putContractCache();
			return contract;
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	private void createFirstRental(Contract c) {
		try {
			RentalHistory rental = new RentalHistory(null, c.getContractStartDate(), PaymentStatus.PENDING, c);
			rentalHistoryService.create(rental);
			contractAsyncService.sendInvoiceByEmail(c, rental);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Transactional
	public void delete(Long id) {
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
	public Contract update(Long id, Contract obj) {
		try {
			Contract entity = repository.getReferenceById(id);
			updateData(entity, obj);
			Contract c = repository.save(entity);
			cacheService.putContractCache();
			return c;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Contract entity, Contract obj) {
		entity.setContractStartDate(obj.getContractStartDate());
		entity.setContractEndDate(obj.getContractEndDate());
		entity.setDefaultRentalValue(obj.getDefaultRentalValue());
		entity.setContractStatus(obj.getContractStatus());
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
