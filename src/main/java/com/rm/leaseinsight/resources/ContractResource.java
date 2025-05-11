package com.rm.leaseinsight.resources;

import java.net.URI;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rm.leaseinsight.dto.req.ContractRequestDTO;
import com.rm.leaseinsight.dto.res.ContractResponseDTO;
import com.rm.leaseinsight.entities.Contract;
import com.rm.leaseinsight.services.ContractService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/contracts")
public class ContractResource {
	@Autowired
	private ContractService service;

	@GetMapping
	public ResponseEntity<List<ContractResponseDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAllCached());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ContractResponseDTO> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<ContractResponseDTO> insert(@RequestBody @Valid ContractRequestDTO obj) {
		ContractResponseDTO contract = service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(contract.getId()).toUri();
		return ResponseEntity.created(uri).body(contract);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<Contract> patch(@PathVariable String id, @RequestBody Contract obj) {
		obj = service.patch(id, obj);
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping(value = "/status/{code}")
	public ResponseEntity<Set<Contract>> findByContractStatus(@PathVariable Integer code) {
		Set<Contract> contractsDTO = service.findByContractStatus(code);
		return ResponseEntity.ok().body(contractsDTO);
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