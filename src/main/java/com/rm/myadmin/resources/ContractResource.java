package com.rm.myadmin.resources;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rm.myadmin.entities.Contract;

@RestController
@RequestMapping(value = "/contracts")
public class ContractResource {

	@GetMapping
	public ResponseEntity<Contract> findAll() {
		Contract c = new Contract(1L, LocalDate.now(), LocalDate.now(), 1500.0, "Andamento");
		return ResponseEntity.ok().body(c);
	}
}