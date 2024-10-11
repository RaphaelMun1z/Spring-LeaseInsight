package com.rm.leaseinsight.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.leaseinsight.entities.Adm;
import com.rm.leaseinsight.repositories.AdmRepository;
import com.rm.leaseinsight.services.exceptions.DataViolationException;
import com.rm.leaseinsight.services.exceptions.DatabaseException;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class AdmService {
	@Autowired
	private AdmRepository repository;

	@Autowired
	private CacheService cacheService;

	@Cacheable("findAllAdm")
	public List<Adm> findAllCached() {
		return findAll();
	}

	public List<Adm> findAll() {
		return repository.findAll();
	}

	public Adm findById(String id) {
		Optional<Adm> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public Adm create(Adm obj) {
		try {
			Adm adm = repository.save(obj);
			cacheService.putAdmCache();
			cacheService.putUserCache();
			return adm;
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	@Transactional
	public void delete(String id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				cacheService.evictAllCacheValues("findAllAdm");
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
	public Adm patch(String id, Adm obj) {
		try {
			Adm entity = repository.getReferenceById(id);
			updateData(entity, obj);
			Adm adm = repository.save(entity);
			cacheService.putAdmCache();
			return adm;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void updateData(Adm entity, Adm obj) {
		if (obj.getName() != null)
			entity.setName(obj.getName());
		if (obj.getEmail() != null)
			entity.setEmail(obj.getEmail());
		if (obj.getPhone() != null)
			entity.setPhone(obj.getPhone());
	}
}
