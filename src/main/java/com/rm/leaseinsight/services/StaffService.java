package com.rm.leaseinsight.services;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.leaseinsight.dto.req.StaffRequestDTO;
import com.rm.leaseinsight.dto.res.StaffResponseDTO;
import com.rm.leaseinsight.entities.FieldViolationMessage;
import com.rm.leaseinsight.entities.Staff;
import com.rm.leaseinsight.repositories.StaffRepository;
import com.rm.leaseinsight.services.async.AccountAsyncService;
import com.rm.leaseinsight.services.exceptions.DataViolationException;
import com.rm.leaseinsight.services.exceptions.DatabaseException;
import com.rm.leaseinsight.services.exceptions.FieldViolationException;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class StaffService {
	@Autowired
	private StaffRepository repository;

	@Autowired
	private AccountAsyncService accountAsyncService;

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
	public StaffResponseDTO create(StaffRequestDTO obj) {
		try {
			String password = generateRandomString(6);
			String encryptedPassword = new BCryptPasswordEncoder().encode(password);
			Staff staff = new Staff(null, obj.getName(), obj.getPhone(), obj.getEmail(), encryptedPassword);
			repository.save(staff);
			cacheService.putStaffCache();
			cacheService.putUserCache();

			sendEmailToStaff(staff, password);

			password = null;

			StaffResponseDTO staffResponse = new StaffResponseDTO(staff);
			return staffResponse;
		} catch (ConstraintViolationException e) {
			List<FieldViolationMessage> errors = e.getConstraintViolations().stream()
					.map(violation -> new FieldViolationMessage(violation.getPropertyPath().toString(),
							violation.getMessage()))
					.toList();
			throw new FieldViolationException(errors);
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void sendEmailToStaff(Staff staff, String password) {
		try {
			accountAsyncService.sendNewAccountEmail(staff, password);
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	@Transactional
	public void delete(String id) {
		try {
			if (!repository.existsById(id)) {
				throw new ResourceNotFoundException(id);
			}

			repository.deleteById(id);
			cacheService.evictAllCacheValues("findAllStaff");
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@PreAuthorize("@authenticatedUserService.hasId(#id)")
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
