package com.rm.myadmin.resources;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rm.myadmin.dto.ContractDTO;
import com.rm.myadmin.entities.Contract;
import com.rm.myadmin.entities.Tenant;
import com.rm.myadmin.services.ContractService;
import com.rm.myadmin.services.TenantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/tenants")
public class TenantResource {
	@Autowired
	private TenantService service;

	@Autowired
	private ContractService contractService;

	@GetMapping
	public ResponseEntity<List<Tenant>> findAll() {
		List<Tenant> list = service.findAllCached();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Tenant> findById(@PathVariable String id) {
		Tenant obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<Tenant> createTenant(@RequestBody @Valid Tenant obj) {
		obj = service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Tenant> update(@PathVariable String id, @RequestBody Tenant obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping(value = "/{id}/contracts")
	public ResponseEntity<Set<ContractDTO>> getContracts(@PathVariable String id) {
		Set<Contract> contracts = contractService.findByTenant(id);
		Set<ContractDTO> list = new HashSet<>();
		for (Contract c : contracts) {
			list.add(new ContractDTO(c));
		}
		return ResponseEntity.ok().body(list);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> validationExceptionHandler(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((err) -> {
			String fieldName = ((FieldError) err).getField();
			String errorMessage = err.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}