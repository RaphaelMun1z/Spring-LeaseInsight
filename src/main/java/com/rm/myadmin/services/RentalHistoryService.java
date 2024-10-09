package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.myadmin.entities.Contract;
import com.rm.myadmin.entities.RentalHistory;
import com.rm.myadmin.repositories.RentalHistoryRepository;
import com.rm.myadmin.services.exceptions.DataViolationException;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

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
}
