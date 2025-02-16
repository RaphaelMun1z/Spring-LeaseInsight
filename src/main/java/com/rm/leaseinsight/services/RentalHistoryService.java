package com.rm.leaseinsight.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.leaseinsight.dto.RentalHistoryMinimalResponseDTO;
import com.rm.leaseinsight.entities.Contract;
import com.rm.leaseinsight.entities.RentalHistory;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.repositories.RentalHistoryRepository;
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
	private CacheService cacheService;

	@Cacheable("findAllRentalHistory")
	public List<RentalHistory> findAllCached() {
		return findAll();
	}

	public List<RentalHistory> findAll() {
		return repository.findAll();
	}

	public RentalHistory findById(String id) {
		Optional<RentalHistory> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Rental History", id));
	}

	public List<RentalHistoryMinimalResponseDTO> findAllMinimal() {
		List<RentalHistory> list = this.findAll();
		List<RentalHistoryMinimalResponseDTO> rentalHistories = new ArrayList<>();

		for (RentalHistory rentalHistory : list) {
			rentalHistories.add(new RentalHistoryMinimalResponseDTO(rentalHistory));
		}

		return rentalHistories;
	}

	@Transactional
	public RentalHistory create(RentalHistory obj) {
		try {
			Contract contract = contractService.findById(obj.getContract().getId());
			RentalHistory rh = new RentalHistory(null, obj.getRentalStartDate(), obj.getPaymentStatus(), contract);
			repository.save(rh);
			cacheService.putRentalHistoryCache();
			return rh;
		} catch (DataIntegrityViolationException e) {
			System.out.println("Erro: " + e.getMessage());
			throw new DataViolationException("Rental History");
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
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
