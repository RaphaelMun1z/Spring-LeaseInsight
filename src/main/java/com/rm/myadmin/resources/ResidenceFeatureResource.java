package com.rm.myadmin.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rm.myadmin.entities.ResidenceFeature;
import com.rm.myadmin.services.ResidenceFeatureService;

@RestController
@RequestMapping(value = "/residence-feature")
public class ResidenceFeatureResource {
	@Autowired
	private ResidenceFeatureService service;

	@GetMapping
	public ResponseEntity<List<ResidenceFeature>> findAll() {
		List<ResidenceFeature> list = service.findAllCached();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ResidenceFeature> findById(@PathVariable Long id) {
		ResidenceFeature obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}