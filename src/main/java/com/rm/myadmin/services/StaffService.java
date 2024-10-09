package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.myadmin.entities.Staff;
import com.rm.myadmin.repositories.StaffRepository;
import com.rm.myadmin.services.exceptions.DataViolationException;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class StaffService {
	@Autowired
	private StaffRepository repository;

	@Autowired
	private CacheService cacheService;

	@Cacheable("findAllStaff")
	public List<Staff> findAllCached() {
		return findAll();
	}

	public List<Staff> findAll() {
		return repository.findAll();
	}

	public Staff findById(String id) {
		Optional<Staff> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Staff", id));
	}

	@Transactional
	public Staff create(Staff obj) {
		try {
			String encryptedPassword = new BCryptPasswordEncoder().encode(obj.getPassword());
			Staff staff = new Staff(null, obj.getName(), obj.getPhone(), obj.getEmail(), encryptedPassword);
			repository.save(staff);
			cacheService.putStaffCache();
			cacheService.putUserCache();
			return staff;
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	@Transactional
	public void delete(String id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				cacheService.evictAllCacheValues("findAllStaff");
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
	public Staff patch(String id, Staff obj) {
		try {
			Staff entity = repository.getReferenceById(id);
			patchData(entity, obj);
			Staff s = repository.save(entity);
			cacheService.putStaffCache();
			return s;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void patchData(Staff entity, Staff obj) {
		if (obj.getName() != null)
			entity.setName(obj.getName());
		if (obj.getEmail() != null)
			entity.setEmail(obj.getEmail());
		if (obj.getPhone() != null)
			entity.setPhone(obj.getPhone());
	}
}
