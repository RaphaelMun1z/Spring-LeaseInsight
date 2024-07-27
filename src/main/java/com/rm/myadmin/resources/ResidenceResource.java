package com.rm.myadmin.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.services.ResidenceService;

@RestController
@RequestMapping(value = "/residences")
public class ResidenceResource {
	@Autowired
	private ResidenceService service;

	@GetMapping
	public ResponseEntity<List<Residence>> findAll() {
		List<Residence> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Residence> findById(@PathVariable Long id) {
		Residence obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}