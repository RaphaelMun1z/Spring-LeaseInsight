package com.rm.myadmin.resources;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.rm.myadmin.dto.ResidenceAddressResponseDTO;
import com.rm.myadmin.entities.ResidenceAddress;
import com.rm.myadmin.services.ResidenceAddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/residence-addresses")
public class ResidenceAddressResource {
	@Autowired
	private ResidenceAddressService service;

	@GetMapping
	public ResponseEntity<List<ResidenceAddressResponseDTO>> findAll() {
		List<ResidenceAddressResponseDTO> residenceAddresses = service.findAllCached().stream()
				.map(ResidenceAddressResponseDTO::new).collect(Collectors.toList());
		return ResponseEntity.ok().body(residenceAddresses);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ResidenceAddress> findById(@PathVariable String id) {
		ResidenceAddress obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<ResidenceAddress> insert(@RequestBody @Valid ResidenceAddress obj) {
		obj = service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<ResidenceAddress> patch(@PathVariable String id, @RequestBody ResidenceAddress obj) {
		obj = service.patch(id, obj);
		return ResponseEntity.ok().body(obj);
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