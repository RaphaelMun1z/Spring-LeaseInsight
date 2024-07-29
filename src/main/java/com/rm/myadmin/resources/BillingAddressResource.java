package com.rm.myadmin.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rm.myadmin.entities.BillingAddress;
import com.rm.myadmin.services.BillingAddressService;

@RestController
@RequestMapping(value = "/billing-addresses")
public class BillingAddressResource {
	@Autowired
	private BillingAddressService service;

	@GetMapping
	public ResponseEntity<List<BillingAddress>> findAll() {
		List<BillingAddress> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<BillingAddress> findById(@PathVariable Long id) {
		BillingAddress obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}