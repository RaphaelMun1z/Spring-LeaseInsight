package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rm.myadmin.entities.Contract;
import com.rm.myadmin.repositories.ContractRepository;

@Service
public class ContractService {
	@Autowired
	private ContractRepository repository;

	public List<Contract> findAll() {
		return repository.findAll();
	}

	public Contract findById(Long id) {
		Optional<Contract> obj = repository.findById(id);
		return obj.get();
	}

	public Contract create(Contract obj) {
		return repository.save(obj);
	}
}
