package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.myadmin.entities.Owner;
import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.repositories.OwnerRepository;
import com.rm.myadmin.services.exceptions.DataViolationException;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class OwnerService {
	@Autowired
	private OwnerRepository repository;

	@Autowired
	private ResidenceService residenceService;

	@Autowired
	private CacheService cacheService;

	@Cacheable("findAllOwner")
	public List<Owner> findAllCached() {
		return findAll();
	}

	public List<Owner> findAll() {
		return repository.findAll();
	}

	public Owner findById(String id) {
		Optional<Owner> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Owner", id));
	}

	@Transactional
	public Owner create(Owner obj) {
		try {
			String encryptedPassword = new BCryptPasswordEncoder().encode(obj.getPassword());
			Owner owner = new Owner(null, obj.getName(), obj.getPhone(), obj.getEmail(), encryptedPassword);
			repository.save(owner);
			cacheService.putOwnerCache();
			cacheService.putUserCache();
			return owner;
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		} catch (IllegalArgumentException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Transactional
	public void delete(String id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				cacheService.evictAllCacheValues("findAllOwner");
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
	public Owner patch(String id, Owner obj) {
		try {
			Owner entity = repository.getReferenceById(id);
			patchData(entity, obj);
			Owner o = repository.save(entity);
			cacheService.putOwnerCache();
			return o;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void patchData(Owner entity, Owner obj) {
		if (obj.getName() != null)
			entity.setName(obj.getName());
		if (obj.getEmail() != null)
			entity.setEmail(obj.getEmail());
		if (obj.getPhone() != null)
			entity.setPhone(obj.getPhone());
	}

	public Set<Residence> findResidences(String id) {
		return residenceService.findByOwner(id);
	}
}
