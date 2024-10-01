package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.myadmin.entities.AdditionalFeature;
import com.rm.myadmin.repositories.AdditionalFeatureRepository;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AdditionalFeatureService {
	@Autowired
	private AdditionalFeatureRepository repository;

	@Autowired
	private CacheService cacheService;

	@Cacheable("findAllAdditionalFeatures")
	public List<AdditionalFeature> findAllCached() {
		return findAll();
	}

	public List<AdditionalFeature> findAll() {
		return repository.findAll();
	}

	public AdditionalFeature findById(String id) {
		Optional<AdditionalFeature> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public AdditionalFeature create(AdditionalFeature obj) {
		AdditionalFeature af = repository.save(obj);
		cacheService.putAdditionalFeatureCache();
		return af;
	}

	@Transactional
	public void delete(String id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				cacheService.evictAllCacheValues("findAllAdditionalFeatures");
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
	public AdditionalFeature update(String id, AdditionalFeature obj) {
		try {
			AdditionalFeature entity = repository.getReferenceById(id);
			updateData(entity, obj);
			AdditionalFeature af = repository.save(entity);
			cacheService.putAdditionalFeatureCache();
			return af;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(AdditionalFeature entity, AdditionalFeature obj) {
		entity.setFeature(obj.getFeature());
	}
}
