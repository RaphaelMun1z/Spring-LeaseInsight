package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rm.myadmin.entities.Owner;
import com.rm.myadmin.repositories.OwnerRepository;

@Service
public class OwnerService {
	@Autowired
	private OwnerRepository repository;

	public List<Owner> findAll() {
		return repository.findAll();
	}

	public Owner findById(Long id) {
		Optional<Owner> obj = repository.findById(id);
		return obj.get();
	}

	public Owner create(Owner obj) {
		return repository.save(obj);
	}
}
