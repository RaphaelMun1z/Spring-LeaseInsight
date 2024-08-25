package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.myadmin.entities.BillingAddress;
import com.rm.myadmin.entities.Tenant;
import com.rm.myadmin.repositories.TenantRepository;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TenantService {
	@Autowired
	private TenantRepository repository;

	@Autowired
	private BillingAddressService billingAddressService;

	@Autowired
	private CacheService cacheService;

	@Cacheable("findAllTenant")
	public List<Tenant> findAllCached() {
		return findAll();
	}

	public List<Tenant> findAll() {
		return repository.findAll();
	}

	public Tenant findById(String id) {
		Optional<Tenant> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public Tenant create(Tenant obj) {
		BillingAddress tba = billingAddressService.findById(obj.getTenantBillingAddress().getId());
		obj.setTenantBillingAddress(tba);
		Tenant tenant = repository.save(obj);
		cacheService.putTenantCache();
		cacheService.putUserCache();
		return tenant;
	}

	@Transactional
	public void delete(String id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				cacheService.evictAllCacheValues("findAllTenant");
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
	public Tenant update(String id, Tenant obj) {
		try {
			Tenant entity = repository.getReferenceById(id);
			updateData(entity, obj);
			Tenant t = repository.save(entity);
			cacheService.putTenantCache();
			return t;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Tenant entity, Tenant obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
		entity.setDateOfBirth(obj.getDateOfBirth());
		entity.setCpf(obj.getCpf());
		entity.setRg(obj.getRg());
		entity.setTenantStatus(obj.getTenantStatus());
	}

}
