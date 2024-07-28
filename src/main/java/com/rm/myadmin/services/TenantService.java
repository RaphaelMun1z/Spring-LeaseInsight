package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rm.myadmin.entities.Tenant;
import com.rm.myadmin.repositories.TenantRepository;

@Service
public class TenantService {
	@Autowired
	private TenantRepository repository;

	public List<Tenant> findAll() {
		return repository.findAll();
	}

	public Tenant findById(Long id) {
		Optional<Tenant> obj = repository.findById(id);
		return obj.get();
	}
}
