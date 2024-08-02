package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rm.myadmin.entities.RentalHistory;
import com.rm.myadmin.repositories.RentalHistoryRepository;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

@Service
public class RentalHistoryService {
	@Autowired
	private RentalHistoryRepository repository;

	@Autowired
	private ContractService contractService;

	public List<RentalHistory> findAll() {
		return repository.findAll();
	}

	public RentalHistory findById(Long id) {
		Optional<RentalHistory> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public RentalHistory create(RentalHistory obj) {
		obj.setContract(contractService.findById(obj.getContract().getId()));
		return repository.save(obj);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
