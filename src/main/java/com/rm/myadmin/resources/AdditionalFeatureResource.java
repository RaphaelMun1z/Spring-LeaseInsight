package com.rm.myadmin.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rm.myadmin.entities.AdditionalFeature;
import com.rm.myadmin.services.AdditionalFeatureService;

@RestController
@RequestMapping(value = "/additional_features")
public class AdditionalFeatureResource {
	@Autowired
	private AdditionalFeatureService service;

	@GetMapping
	public ResponseEntity<List<AdditionalFeature>> findAll() {
		List<AdditionalFeature> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<AdditionalFeature> findById(@PathVariable Long id) {
		AdditionalFeature obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<AdditionalFeature> insert(@RequestBody AdditionalFeature obj) {
		obj = service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
}