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

import com.rm.myadmin.entities.Owner;
import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.repositories.OwnerRepository;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

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
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public Owner create(Owner obj) {
		Owner owner = repository.save(obj);
		cacheService.putOwnerCache();
		cacheService.putUserCache();
		return owner;
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
	public Owner update(String id, Owner obj) {
		try {
			Owner entity = repository.getReferenceById(id);
			updateData(entity, obj);
			Owner o = repository.save(entity);
			cacheService.putOwnerCache();
			return o;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Owner entity, Owner obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
	}

	public Set<Residence> findResidences(String id) {
		return residenceService.findByOwner(id);
	}
}
