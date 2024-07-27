package com.rm.myadmin.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rm.myadmin.entities.RentalHistory;
import com.rm.myadmin.services.RentalHistoryService;

@RestController
@RequestMapping(value = "/rental-histories")
public class RentalHistoryResource {
	@Autowired
	private RentalHistoryService service;

	@GetMapping
	public ResponseEntity<List<RentalHistory>> findAll() {
		List<RentalHistory> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<RentalHistory> findById(@PathVariable Long id) {
		RentalHistory obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}