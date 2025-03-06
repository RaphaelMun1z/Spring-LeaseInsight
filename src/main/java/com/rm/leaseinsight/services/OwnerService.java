package com.rm.leaseinsight.services;

import java.security.SecureRandom;
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

import com.rm.leaseinsight.dto.OwnerRequestDTO;
import com.rm.leaseinsight.dto.OwnerResponseDTO;
import com.rm.leaseinsight.entities.Owner;
import com.rm.leaseinsight.entities.Residence;
import com.rm.leaseinsight.repositories.OwnerRepository;
import com.rm.leaseinsight.services.exceptions.DataViolationException;
import com.rm.leaseinsight.services.exceptions.DatabaseException;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

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

	public static String generateRandomString(int length) {
		String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom RANDOM = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int ii = 0; ii < length; ii++) {
			sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
		}
		return sb.toString();
	}
	
	@Transactional
	public OwnerResponseDTO create(OwnerRequestDTO obj) {
		try {
			String encryptedPassword = new BCryptPasswordEncoder().encode(generateRandomString(10));
			Owner owner = new Owner(null, obj.getName(), obj.getPhone(), obj.getEmail(), encryptedPassword);
			repository.save(owner);
			cacheService.putOwnerCache();
			cacheService.putUserCache();
			OwnerResponseDTO ownerResponse = new OwnerResponseDTO(owner);
			return ownerResponse;
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
