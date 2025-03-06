package com.rm.leaseinsight.resources;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import com.rm.leaseinsight.dto.req.OwnerRequestDTO;
import com.rm.leaseinsight.dto.res.OwnerResponseDTO;
import com.rm.leaseinsight.dto.res.ResidenceResponseDTO;
import com.rm.leaseinsight.entities.Owner;
import com.rm.leaseinsight.entities.Residence;
import com.rm.leaseinsight.services.OwnerService;
import com.rm.leaseinsight.services.ResidenceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/owners")
public class OwnerResource {
	@Autowired
	private OwnerService service;

	@Autowired
	private ResidenceService residenceService;

	@GetMapping
	public ResponseEntity<List<OwnerResponseDTO>> findAll() {
		List<OwnerResponseDTO> owners = service.findAllCached().stream().map(OwnerResponseDTO::new)
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(owners);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Owner> findById(@PathVariable String id) {
		Owner obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<OwnerResponseDTO> insert(@RequestBody @Valid OwnerRequestDTO obj) {
		OwnerResponseDTO owner = service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(owner.getId()).toUri();
		return ResponseEntity.created(uri).body(owner);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<Owner> patch(@PathVariable String id, @RequestBody Owner obj) {
		obj = service.patch(id, obj);
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping(value = "/{id}/residences")
	public ResponseEntity<Set<ResidenceResponseDTO>> getResidences(@PathVariable String id) {
		Set<Residence> list = residenceService.findByOwner(id);
		Set<ResidenceResponseDTO> residences = new HashSet<>();
		for (Residence residence : list) {
			residences.add(new ResidenceResponseDTO(residence));
		}
		return ResponseEntity.ok().body(residences);
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