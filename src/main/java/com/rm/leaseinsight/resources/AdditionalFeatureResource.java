package com.rm.leaseinsight.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.rm.leaseinsight.dto.res.AdditionalFeatureResponseDTO;
import com.rm.leaseinsight.entities.AdditionalFeature;
import com.rm.leaseinsight.services.AdditionalFeatureService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/additional-features")
public class AdditionalFeatureResource {
	@Autowired
	private AdditionalFeatureService service;

	@GetMapping
	public ResponseEntity<List<AdditionalFeatureResponseDTO>> findAll() {
		List<AdditionalFeature> list = service.findAllCached();
		List<AdditionalFeatureResponseDTO> additionalFeatures = new ArrayList<>();

		for (AdditionalFeature additionalFeature : list) {
			additionalFeatures.add(new AdditionalFeatureResponseDTO(additionalFeature));
		}
		return ResponseEntity.ok().body(additionalFeatures);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<AdditionalFeature> findById(@PathVariable String id) {
		AdditionalFeature obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<AdditionalFeature> insert(@RequestBody @Valid AdditionalFeature obj) {
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
	public ResponseEntity<AdditionalFeature> patch(@PathVariable String id, @RequestBody AdditionalFeature obj) {
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