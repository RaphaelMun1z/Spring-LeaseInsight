package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.myadmin.entities.Adm;
import com.rm.myadmin.repositories.AdmRepository;
import com.rm.myadmin.services.exceptions.DataViolationException;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

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
	public Adm update(String id, Adm obj) {
		try {
			Adm entity = repository.getReferenceById(id);
			updateData(entity, obj);
			Adm adm = repository.save(entity);
			cacheService.putAdmCache();
			return adm;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Adm entity, Adm obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
	}
}
