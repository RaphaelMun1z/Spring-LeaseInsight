package com.rm.myadmin.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rm.myadmin.entities.Tenant;
import com.rm.myadmin.services.TenantService;

@RestController
@RequestMapping(value = "/tenants")
public class TenantResource {
	@Autowired
	private TenantService service;

	@GetMapping
	public ResponseEntity<List<Tenant>> findAll() {
		List<Tenant> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Tenant> findById(@PathVariable Long id) {
		Tenant obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}