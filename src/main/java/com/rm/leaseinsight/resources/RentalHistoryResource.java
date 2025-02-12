package com.rm.leaseinsight.resources;

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

import com.rm.leaseinsight.dto.RentalHistoryMinimalResponseDTO;
import com.rm.leaseinsight.dto.RentalHistoryResponseDTO;
import com.rm.leaseinsight.entities.RentalHistory;
import com.rm.leaseinsight.services.RentalHistoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/rental-histories")
public class RentalHistoryResource {
	@Autowired
	private RentalHistoryService service;

	@GetMapping
	public ResponseEntity<List<RentalHistoryResponseDTO>> findAll() {
		List<RentalHistoryResponseDTO> rentalsHistory = service.findAllCached().stream()
				.map(RentalHistoryResponseDTO::new).collect(Collectors.toList());
		return ResponseEntity.ok().body(rentalsHistory);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<RentalHistoryResponseDTO> findById(@PathVariable String id) {
		RentalHistory obj = service.findById(id);
		RentalHistoryResponseDTO rentalHistory = new RentalHistoryResponseDTO(obj);
		return ResponseEntity.ok().body(rentalHistory);
	}

	@GetMapping(value = "/minimal")
	public ResponseEntity<List<RentalHistoryMinimalResponseDTO>> findRentalHistoryMinimal() {
		List<RentalHistoryMinimalResponseDTO> rentalHistories = service.findAllMinimal();
		return ResponseEntity.ok().body(rentalHistories);
	}

	@PostMapping
	public ResponseEntity<RentalHistoryResponseDTO> insert(@RequestBody @Valid RentalHistory obj) {
		obj = service.create(obj);
		RentalHistoryResponseDTO rentalHistory = new RentalHistoryResponseDTO(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(rentalHistory.getId())
				.toUri();
		return ResponseEntity.created(uri).body(rentalHistory);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<RentalHistory> patch(@PathVariable String id, @RequestBody RentalHistory obj) {
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