package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

	public List<AdditionalFeature> findAll() {
		return repository.findAll();
	}

	public AdditionalFeature findById(Long id) {
		Optional<AdditionalFeature> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public AdditionalFeature create(AdditionalFeature obj) {
		return repository.save(obj);
	}

	@Transactional
	public void delete(Long id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
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
	public AdditionalFeature update(Long id, AdditionalFeature obj) {
		try {
			AdditionalFeature entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(AdditionalFeature entity, AdditionalFeature obj) {
		entity.setFeature(obj.getFeature());
	}
}
