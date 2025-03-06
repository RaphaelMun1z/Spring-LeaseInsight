package com.rm.leaseinsight.services;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.leaseinsight.dto.TenantRequestDTO;
import com.rm.leaseinsight.dto.TenantResponseDTO;
import com.rm.leaseinsight.entities.BillingAddress;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.repositories.TenantRepository;
import com.rm.leaseinsight.services.async.TenantAsyncService;
import com.rm.leaseinsight.services.exceptions.DataViolationException;
import com.rm.leaseinsight.services.exceptions.DatabaseException;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class TenantService {
	@Autowired
	private TenantRepository repository;

	@Autowired
	private BillingAddressService billingAddressService;

	@Autowired
	private TenantAsyncService tenantAsyncService;

	@Autowired
	private CacheService cacheService;

	@Cacheable("findAllTenant")
	public List<Tenant> findAllCached() {
		return findAll();
	}

	public List<Tenant> findAll() {
		return repository.findAll();
	}

	public Tenant findById(String id) {
		Optional<Tenant> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Tenant", id));
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
	public TenantResponseDTO create(TenantRequestDTO obj) {
		try {
			String encryptedPassword = new BCryptPasswordEncoder().encode(generateRandomString(10));
			BillingAddress tba = billingAddressService.findById(obj.getTenantBillingAddress().getId());
			Tenant tenant = new Tenant(null, obj.getName(), obj.getPhone(), obj.getEmail(), encryptedPassword,
					obj.getDateOfBirth(), obj.getCpf(), obj.getRg(), LocalDate.now(), obj.getTenantStatus(),
					tba);
			repository.save(tenant);
			cacheService.putTenantCache();
			cacheService.putUserCache();
			TenantResponseDTO tenantResponse = new TenantResponseDTO(tenant);
			tenantAsyncService.sendNewTenantEmail(tenantResponse);
			return tenantResponse;
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		} catch (DataViolationException e) {
			throw e;
		}
	}

	@Transactional
	public void delete(String id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				cacheService.evictAllCacheValues("findAllTenant");
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
	public Tenant patch(String id, Tenant obj) {
		try {
			Tenant entity = repository.getReferenceById(id);
			patchData(entity, obj);
			Tenant t = repository.save(entity);
			cacheService.putTenantCache();
			return t;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getClass());
			return null;
		}
	}

	private void patchData(Tenant entity, Tenant obj) {
		if (obj.getName() != null)
			entity.setName(obj.getName());
		if (obj.getEmail() != null)
			entity.setEmail(obj.getEmail());
		if (obj.getPhone() != null)
			entity.setPhone(obj.getPhone());
		if (obj.getDateOfBirth() != null)
			entity.setDateOfBirth(obj.getDateOfBirth());
		if (obj.getCpf() != null)
			entity.setCpf(obj.getCpf());
		if (obj.getRg() != null)
			entity.setRg(obj.getRg());
		if (obj.getTenantStatus() != null)
			entity.setTenantStatus(obj.getTenantStatus());
	}

}
