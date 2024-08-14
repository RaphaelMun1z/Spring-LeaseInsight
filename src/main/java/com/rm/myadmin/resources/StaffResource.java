package com.rm.myadmin.resources;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.rm.myadmin.dto.StaffRegisterRequestDTO;
import com.rm.myadmin.entities.Staff;
import com.rm.myadmin.repositories.StaffRepository;
import com.rm.myadmin.services.StaffService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/staffs")
public class StaffResource {
	@Autowired
	private StaffService service;

	@Autowired
	private StaffRepository repository;

	@GetMapping
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<List<Staff>> findAll() {
		List<Staff> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<Staff> insert(@RequestBody @Valid StaffRegisterRequestDTO obj) {
		if (repository.findByEmail(obj.email()) != null)
			return ResponseEntity.badRequest().build();

		String encryptedPassword = new BCryptPasswordEncoder().encode(obj.password());
		Staff user = new Staff(null, obj.name(), obj.phone(), obj.email(), encryptedPassword);

		Staff staff = service.create(user);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(staff.getId()).toUri();
		return ResponseEntity.created(uri).body(staff);
	}

	@GetMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<Staff> findById(@PathVariable String id) {
		Staff obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<Staff> update(@PathVariable String id, @RequestBody Staff obj) {
		obj = service.update(id, obj);
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