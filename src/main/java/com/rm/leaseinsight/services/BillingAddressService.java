package com.rm.leaseinsight.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.leaseinsight.dto.res.UserDetailsResponseDTO;
import com.rm.leaseinsight.entities.BillingAddress;
import com.rm.leaseinsight.entities.FieldViolationMessage;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.repositories.BillingAddressRepository;
import com.rm.leaseinsight.services.exceptions.DataViolationException;
import com.rm.leaseinsight.services.exceptions.DatabaseException;
import com.rm.leaseinsight.services.exceptions.FieldViolationException;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class BillingAddressService {
	@Autowired
	private BillingAddressRepository repository;

	@Lazy
	@Autowired
	private TenantService tenantService;

	@Autowired
	private CacheService cacheService;

	@Cacheable("findAllBillingAddress")
	public List<BillingAddress> findAllCached() {
		return findAll();
	}

	public List<BillingAddress> findAll() {
		return repository.findAll();
	}

	public BillingAddress findById(String id) {
		Optional<BillingAddress> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Billing Address", id));
	}

	public BillingAddress findByAuthUser() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth == null || !auth.isAuthenticated()) {
				throw new RuntimeException();
			}
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			UserDetailsResponseDTO user = new UserDetailsResponseDTO(userDetails);
			Tenant tenant = tenantService.findById(user.getId());
			BillingAddress billingAddress = tenant.getTenantBillingAddress();
			return billingAddress;
		} catch (DataIntegrityViolationException e) {
			System.out.println("Erro ao detalhar usuário: " + e.getMessage());
			throw new DatabaseException(e.getMessage());
		} catch (Exception e) {
			System.out.println("Erro ao detalhar usuário: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Transactional
	public BillingAddress create(BillingAddress obj) {
		try {
			BillingAddress ba = repository.save(obj);
			cacheService.putBillingAddressCache();
			return ba;
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	@Transactional
	public void delete(String id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				cacheService.evictAllCacheValues("findAllBillingAddress");
			} else {
				throw new ResourceNotFoundException(id);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public boolean isOwner(String billingAddressId) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth == null || !auth.isAuthenticated()) {
				throw new RuntimeException();
			}
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			UserDetailsResponseDTO user = new UserDetailsResponseDTO(userDetails);
			Tenant tenant = tenantService.findById(user.getId());

			return tenant.getTenantBillingAddress().getId().equals(billingAddressId);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Erro ao detalhar usuário: " + e.getMessage());
			throw new DatabaseException(e.getMessage());
		} catch (Exception e) {
			System.out.println("Erro ao detalhar usuário: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'STAFF') or @billingAddressService.isOwner(#id)")
	@Transactional
	public BillingAddress patch(String id, BillingAddress obj) {
		try {
			BillingAddress entity = repository.getReferenceById(id);
			patchData(entity, obj);
			BillingAddress ba = repository.save(entity);
			cacheService.putBillingAddressCache();
			return ba;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
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

	private void patchData(BillingAddress entity, BillingAddress obj) {
		if (obj.getNumber() != null)
			entity.setNumber(obj.getNumber());
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
