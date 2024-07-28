package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rm.myadmin.entities.ResidenceFeature;
import com.rm.myadmin.repositories.ResidenceFeatureRepository;

@Service
public class ResidenceFeatureService {
	@Autowired
	private ResidenceFeatureRepository repository;

	public List<ResidenceFeature> findAll() {
		return repository.findAll();
	}

	public ResidenceFeature findById(Long id) {
		Optional<ResidenceFeature> obj = repository.findById(id);
		return obj.get();
	}
}
