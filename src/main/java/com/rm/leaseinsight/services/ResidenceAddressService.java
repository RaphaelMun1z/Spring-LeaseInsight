package com.rm.leaseinsight.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.leaseinsight.entities.ResidenceAddress;
import com.rm.leaseinsight.repositories.ResidenceAddressRepository;
import com.rm.leaseinsight.services.exceptions.DataViolationException;
import com.rm.leaseinsight.services.exceptions.DatabaseException;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class ResidenceAddressService {
	@Autowired
	private ResidenceAddressRepository repository;

	@Autowired
	private CacheService cacheService;

	@Cacheable("findAllResidenceAddress")
	public List<ResidenceAddress> findAllCached() {
		return findAll();
	}

	public List<ResidenceAddress> findAll() {
		return repository.findAll();
	}

	public ResidenceAddress findById(String id) {
		Optional<ResidenceAddress> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Residence Address", id));
	}

	@Transactional
	public ResidenceAddress create(ResidenceAddress obj) {
		System.out.println("R: " + obj);
		try {
			ResidenceAddress ra = repository.save(obj);
			cacheService.putResidenceAddressCache();
			return ra;
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	@Transactional
	public void delete(String id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				cacheService.evictAllCacheValues("findAllResidenceAddress");
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
	public ResidenceAddress patch(String id, ResidenceAddress obj) {
		try {
			ResidenceAddress entity = repository.getReferenceById(id);
			patchData(entity, obj);
			ResidenceAddress ra = repository.save(entity);
			cacheService.putResidenceAddressCache();
			return ra;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void patchData(ResidenceAddress entity, ResidenceAddress obj) {
		if (obj.getStreet() != null)
			entity.setStreet(obj.getStreet());
		if (obj.getDistrict() != null)
			entity.setDistrict(obj.getDistrict());
		if (obj.getCity() != null)
			entity.setCity(obj.getCity());
		if (obj.getState() != null)
			entity.setState(obj.getState());
		if (obj.getCountry() != null)
			entity.setCountry(obj.getCountry());
		if (obj.getCep() != null)
			entity.setCep(obj.getCep());
		if (obj.getComplement() != null)
			entity.setComplement(obj.getComplement());
	}
}
