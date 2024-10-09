package com.rm.myadmin.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

import com.rm.myadmin.dto.ContractDTO;
import com.rm.myadmin.dto.ResidenceFeatureDTO;
import com.rm.myadmin.dto.ResidenceResponseDTO;
import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.entities.ResidenceFeature;
import com.rm.myadmin.entities.pk.ResidenceFeaturePK;
import com.rm.myadmin.services.ResidenceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/residences")
public class ResidenceResource {
	@Autowired
	private ResidenceService service;

	@GetMapping
	public ResponseEntity<List<ResidenceResponseDTO>> findAll() {
		List<Residence> list = service.findAllCached();
		List<ResidenceResponseDTO> residences = new ArrayList<>();

		for (Residence residence : list) {
			residences.add(new ResidenceResponseDTO(residence));
		}
		return ResponseEntity.ok().body(residences);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Residence> findById(@PathVariable String id) {
		Residence obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<ResidenceResponseDTO> insert(@RequestBody @Valid Residence obj) {
		obj = service.create(obj);
		ResidenceResponseDTO residence = new ResidenceResponseDTO(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(residence);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<Residence> patch(@PathVariable String id, @RequestBody Residence obj) {
		obj = service.patch(id, obj);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping(value = "/add-feature")
	public ResponseEntity<ResidenceFeaturePK> addFeature(@RequestBody ResidenceFeature obj) {
		ResidenceFeature rf = service.addFeature(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(rf.getId()).toUri();
		return ResponseEntity.created(uri).body(rf.getId());
	}

	@GetMapping(value = "/{id}/features")
	public ResponseEntity<Set<ResidenceFeatureDTO>> getFeatures(@PathVariable String id) {
		Set<ResidenceFeatureDTO> features = service.getFeatures(id);
		return ResponseEntity.ok().body(features);
	}

	@Transactional(readOnly = true)
	@GetMapping(value = "/{id}/contract")
	public ResponseEntity<ContractDTO> getContract(@PathVariable String id) {
		ContractDTO contractDTO = service.getCurrentContract(id);
		return ResponseEntity.ok().body(contractDTO);
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