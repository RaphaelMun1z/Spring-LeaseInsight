package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rm.myadmin.entities.RentalHistory;
import com.rm.myadmin.repositories.RentalHistoryRepository;

@Service
public class RentalHistoryService {
	@Autowired
	private RentalHistoryRepository repository;

	public List<RentalHistory> findAll() {
		return repository.findAll();
	}

	public RentalHistory findById(Long id) {
		Optional<RentalHistory> obj = repository.findById(id);
		return obj.get();
	}

	public RentalHistory create(RentalHistory obj) {
		return repository.save(obj);
	}
}
