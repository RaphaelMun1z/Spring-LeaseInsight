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

import com.rm.myadmin.entities.BillingAddress;
import com.rm.myadmin.entities.Tenant;
import com.rm.myadmin.entities.enums.TemplatesEnum;
import com.rm.myadmin.repositories.TenantRepository;
import com.rm.myadmin.services.exceptions.DataViolationException;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TenantService {
	@Autowired
	private TenantRepository repository;

	@Autowired
	private BillingAddressService billingAddressService;

	@Autowired
	private EmailService emailService;

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

	@Transactional
	public Tenant create(Tenant obj) {
		try {
			String encryptedPassword = new BCryptPasswordEncoder().encode(obj.getPassword());
			BillingAddress tba = billingAddressService.findById(obj.getTenantBillingAddress().getId());
			Tenant tenant = new Tenant(null, obj.getName(), obj.getPhone(), obj.getEmail(), encryptedPassword,
					obj.getDateOfBirth(), obj.getCpf(), obj.getRg(), obj.getRegistrationDate(), obj.getTenantStatus(),
					tba);
			repository.save(tenant);
			cacheService.putTenantCache();
			cacheService.putUserCache();
			sendNewTenantEmail(tenant);
			return tenant;
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
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
	public Tenant update(String id, Tenant obj) {
		try {
			Tenant entity = repository.getReferenceById(id);
			updateData(entity, obj);
			Tenant t = repository.save(entity);
			cacheService.putTenantCache();
			return t;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Tenant entity, Tenant obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
		entity.setDateOfBirth(obj.getDateOfBirth());
		entity.setCpf(obj.getCpf());
		entity.setRg(obj.getRg());
		entity.setTenantStatus(obj.getTenantStatus());
	}

	private void sendNewTenantEmail(Tenant t) {
		emailService.sendEmail(TemplatesEnum.WELCOME, t.getName(), t.getEmail(), "Bem-vindo(a) à LeaseInsight");
	}

}
