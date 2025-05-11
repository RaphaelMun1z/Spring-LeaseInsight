package com.rm.leaseinsight.services;

import java.security.SecureRandom;
import java.time.LocalDate;
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

import com.rm.leaseinsight.dto.req.TenantPatchRequestDTO;
import com.rm.leaseinsight.dto.req.TenantRequestDTO;
import com.rm.leaseinsight.dto.res.TenantResponseDTO;
import com.rm.leaseinsight.entities.BillingAddress;
import com.rm.leaseinsight.entities.FieldViolationMessage;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.repositories.TenantRepository;
import com.rm.leaseinsight.services.async.TenantAsyncService;
import com.rm.leaseinsight.services.exceptions.DataViolationException;
import com.rm.leaseinsight.services.exceptions.DatabaseException;
import com.rm.leaseinsight.services.exceptions.FieldViolationException;
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
					obj.getDateOfBirth(), obj.getCpf(), obj.getRg(), LocalDate.now(), obj.getTenantStatus(), tba);
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
		} catch (ConstraintViolationException e) {
			List<FieldViolationMessage> errors = e.getConstraintViolations().stream()
					.map(violation -> new FieldViolationMessage(violation.getPropertyPath().toString(),
							violation.getMessage()))
					.toList();
			throw new FieldViolationException(errors);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Transactional
	public void delete(String id) {
		try {
			if (!repository.existsById(id)) {
				throw new ResourceNotFoundException(id);
			}

			Tenant t = this.findById(id);
			int qntContracts = t.getContracts().size();
			int qntReports = t.getReports().size();

			if (qntContracts > 0) {
				throw new DatabaseException("Erro de integridade referencial: o inquilino está vinculado a "
						+ qntContracts + " contrato(s).");
			}

			if (qntReports > 0) {
				throw new DatabaseException(
						"Erro de integridade referencial: o inquilino está vinculado a " + qntReports + " relato(s).");
			}

			repository.deleteById(id);
			cacheService.evictAllCacheValues("findAllTenant");
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DatabaseException e) {
			throw new DatabaseException(e.getMessage());
		} catch (Exception e) {
			System.out.println("Classe: " + e.getClass());
			throw new DatabaseException("Erro: " + e.getMessage());
		}
	}

	@PreAuthorize("@authenticatedUserService.hasId(#id)")
	@Transactional
	public TenantResponseDTO patch(String id, TenantPatchRequestDTO obj) {
		try {
			Tenant entity = repository.getReferenceById(id);
			patchData(entity, obj);
			Tenant savedTenant = repository.save(entity);
			cacheService.putTenantCache();
			TenantResponseDTO tenantDTO = new TenantResponseDTO(savedTenant);
			return tenantDTO;
		} catch (EntityNotFoundException e) {
			System.out.println("Erro ao atualizar inquilino: " + e.getMessage());
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			List<FieldViolationMessage> errors = e.getConstraintViolations().stream()
					.map(violation -> new FieldViolationMessage(violation.getPropertyPath().toString(),
							violation.getMessage()))
					.toList();
			throw new FieldViolationException(errors);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Erro ao atualizar inquilino: " + e.getMessage());
			throw new DataViolationException();
		} catch (Exception e) {
			System.out.println("Erro ao atualizar inquilino: " + e.getMessage());
			return null;
		}
	}

	private void patchData(Tenant entity, TenantPatchRequestDTO obj) {
		if (obj.getName() != null)
			entity.setName(obj.getName());
		if (obj.getEmail() != null)
			entity.setEmail(obj.getEmail());
		if (obj.getPhone() != null)
			entity.setPhone(obj.getPhone());
		if (obj.getDateOfBirth() != null)
			entity.setDateOfBirth(obj.getDateOfBirth());
		if (obj.getTenantStatus() != null)
			entity.setTenantStatus(obj.getTenantStatus());
		if (obj.getRg() != null)
			entity.setRg(obj.getRg());
		if (obj.getCpf() != null)
			entity.setCpf(obj.getCpf());
	}

}
