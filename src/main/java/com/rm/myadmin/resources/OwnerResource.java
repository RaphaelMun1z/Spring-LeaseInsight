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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.rm.myadmin.dto.OwnerRegisterRequestDTO;
import com.rm.myadmin.dto.ResidenceDTO;
import com.rm.myadmin.entities.Owner;
import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.repositories.OwnerRepository;
import com.rm.myadmin.services.OwnerService;
import com.rm.myadmin.services.ResidenceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/owners")
public class OwnerResource {
	@Autowired
	private OwnerService service;

	@Autowired
	private ResidenceService residenceService;

	@Autowired
	private OwnerRepository repository;

	@GetMapping
	@PreAuthorize("hasAnyRole('ADM', 'STAFF')")
	public ResponseEntity<List<Owner>> findAll() {
		List<Owner> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADM', 'STAFF')")
	public ResponseEntity<Owner> findById(@PathVariable String id) {
		Owner obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('ADM', 'STAFF')")
	public ResponseEntity<Owner> insert(@RequestBody @Valid OwnerRegisterRequestDTO obj) {
		if (repository.findByEmail(obj.email()) != null)
			return ResponseEntity.badRequest().build();

		String encryptedPassword = new BCryptPasswordEncoder().encode(obj.password());
		Owner user = new Owner(null, obj.name(), obj.phone(), obj.email(), encryptedPassword);

		Owner owner = service.create(user);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(owner.getId()).toUri();
		return ResponseEntity.created(uri).body(owner);
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADM', 'STAFF')")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADM', 'STAFF')")
	public ResponseEntity<Owner> update(@PathVariable String id, @RequestBody Owner obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping(value = "/{id}/residences")
	@PreAuthorize("hasAnyRole('ADM', 'STAFF')")
	public ResponseEntity<Set<ResidenceDTO>> getResidences(@PathVariable String id) {
		Set<Residence> list = residenceService.findByOwner(id);
		Set<ResidenceDTO> residences = new HashSet<>();
		for (Residence residence : list) {
			residences.add(new ResidenceDTO(residence));
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