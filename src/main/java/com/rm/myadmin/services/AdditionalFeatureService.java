package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rm.myadmin.entities.AdditionalFeature;
import com.rm.myadmin.repositories.AdditionalFeatureRepository;

@Service
public class AdditionalFeatureService {
	@Autowired
	private AdditionalFeatureRepository repository;

	public List<AdditionalFeature> findAll() {
		return repository.findAll();
	}

	public AdditionalFeature findById(Long id) {
		Optional<AdditionalFeature> obj = repository.findById(id);
		return obj.get();
	}

	public AdditionalFeature create(AdditionalFeature obj) {
		return repository.save(obj);
	}
}
