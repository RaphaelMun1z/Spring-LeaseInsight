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

import com.rm.myadmin.entities.RentalHistory;
import com.rm.myadmin.repositories.RentalHistoryRepository;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

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

	public RentalHistory findById(Long id) {
		Optional<RentalHistory> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public RentalHistory create(RentalHistory obj) {
		obj.setContract(contractService.findById(obj.getContract().getId()));
		RentalHistory rh = repository.save(obj);
		cacheService.putRentalHistoryCache();
		return rh;
	}

	@Transactional
	public void delete(Long id) {
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
	public RentalHistory update(Long id, RentalHistory obj) {
		try {
			RentalHistory entity = repository.getReferenceById(id);
			updateData(entity, obj);
			RentalHistory rh = repository.save(entity);
			cacheService.putRentalHistoryCache();
			return rh;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(RentalHistory entity, RentalHistory obj) {
		entity.setPaymentStatus(obj.getPaymentStatus());
	}
}
