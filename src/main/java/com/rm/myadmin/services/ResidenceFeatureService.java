package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.myadmin.entities.ResidenceFeature;
import com.rm.myadmin.repositories.ResidenceFeatureRepository;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

@Service
public class ResidenceFeatureService {
	@Autowired
	private ResidenceFeatureRepository repository;

	@Autowired
	private CacheService cacheService;

	@Cacheable("findAllResidenceFeature")
	public List<ResidenceFeature> findAllCached() {
		return findAll();
	}

	public List<ResidenceFeature> findAll() {
		return repository.findAll();
	}

	public ResidenceFeature findById(Long id) {
		Optional<ResidenceFeature> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public ResidenceFeature create(ResidenceFeature obj) {
		ResidenceFeature rf = repository.save(obj);
		cacheService.putResidenceFeatureCache();
		return rf;
	}

	@Transactional
	public void delete(Long id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				cacheService.evictAllCacheValues("findAllResidenceFeature");
			} else {
				throw new ResourceNotFoundException(id);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
