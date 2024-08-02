package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rm.myadmin.entities.ResidenceAddress;
import com.rm.myadmin.repositories.ResidenceAddressRepository;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

@Service
public class ResidenceAddressService {
	@Autowired
	private ResidenceAddressRepository repository;

	public List<ResidenceAddress> findAll() {
		return repository.findAll();
	}

	public ResidenceAddress findById(Long id) {
		Optional<ResidenceAddress> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public ResidenceAddress create(ResidenceAddress obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
