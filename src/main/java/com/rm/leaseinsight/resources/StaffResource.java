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

import com.rm.leaseinsight.dto.StaffRequestDTO;
import com.rm.leaseinsight.dto.StaffResponseDTO;
import com.rm.leaseinsight.entities.Staff;
import com.rm.leaseinsight.services.StaffService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/staffs")
public class StaffResource {
	@Autowired
	private StaffService service;

	@GetMapping
	public ResponseEntity<List<StaffResponseDTO>> findAll() {
		List<Staff> list = service.findAllCached();
		List<StaffResponseDTO> staffs = new ArrayList<>();

		for (Staff staff : list) {
			staffs.add(new StaffResponseDTO(staff));
		}
		return ResponseEntity.ok().body(staffs);
	}

	@PostMapping
	public ResponseEntity<StaffResponseDTO> insert(@RequestBody @Valid StaffRequestDTO obj) {
		StaffResponseDTO staff = service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(staff.getId()).toUri();
		return ResponseEntity.created(uri).body(staff);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Staff> findById(@PathVariable String id) {
		Staff obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<Staff> patch(@PathVariable String id, @RequestBody Staff obj) {
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