package com.rm.leaseinsight.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.leaseinsight.dto.res.AdmResponseDTO;
import com.rm.leaseinsight.dto.res.OwnerResponseDTO;
import com.rm.leaseinsight.dto.res.StaffResponseDTO;
import com.rm.leaseinsight.dto.res.TenantResponseDTO;
import com.rm.leaseinsight.dto.res.UserDetailsResponseDTO;
import com.rm.leaseinsight.dto.res.UserResponseDTO;
import com.rm.leaseinsight.entities.Adm;
import com.rm.leaseinsight.entities.Owner;
import com.rm.leaseinsight.entities.Staff;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.entities.User;
import com.rm.leaseinsight.repositories.UserRepository;
import com.rm.leaseinsight.services.exceptions.DatabaseException;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {
	@Autowired
	private UserRepository<User> repository;

	@Autowired
	private CacheService cacheService;

	@Cacheable("findAllUser")
	public List<User> findAllCached() {
		return findAll();
	}

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(String id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("User", id));
	}

	public UserResponseDTO getAuthenticatedUser() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth == null || !auth.isAuthenticated()) {
				throw new RuntimeException();
			}
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			UserDetailsResponseDTO user = new UserDetailsResponseDTO(userDetails);
			User authenticatedUser = findById(user.getId());

			if (authenticatedUser instanceof Tenant) {
				return new TenantResponseDTO((Tenant) authenticatedUser);
			} else if (authenticatedUser instanceof Staff) {
				return new StaffResponseDTO((Staff) authenticatedUser);
			} else if (authenticatedUser instanceof Adm) {
				return new AdmResponseDTO((Adm) authenticatedUser);
			} else if (authenticatedUser instanceof Owner) {
				return new OwnerResponseDTO((Owner) authenticatedUser);
			} else {
				throw new RuntimeException("Tipo de usuário desconhecido");
			}
		} catch (DataIntegrityViolationException e) {
			System.out.println("Erro ao detalhar usuário: " + e.getMessage());
			throw new DatabaseException(e.getMessage());
		} catch (Exception e) {
			System.out.println("Erro ao detalhar usuário: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Transactional
	public void delete(String id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				cacheService.evictAllCacheValues("findAllUser");
			} else {
				throw new ResourceNotFoundException(id);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
