package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rm.myadmin.entities.BillingAddress;
import com.rm.myadmin.repositories.BillingAddressRepository;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

@Service
public class BillingAddressService {
	@Autowired
	private BillingAddressRepository repository;

	public List<BillingAddress> findAll() {
		return repository.findAll();
	}

	public BillingAddress findById(Long id) {
		Optional<BillingAddress> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public BillingAddress create(BillingAddress obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
