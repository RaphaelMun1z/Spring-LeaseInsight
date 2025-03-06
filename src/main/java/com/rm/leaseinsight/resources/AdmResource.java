package com.rm.leaseinsight.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.rm.leaseinsight.dto.req.AdmRegisterRequestDTO;
import com.rm.leaseinsight.dto.res.AdmResponseDTO;
import com.rm.leaseinsight.entities.Adm;
import com.rm.leaseinsight.repositories.AdmRepository;
import com.rm.leaseinsight.services.AdmService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/adm")
public class AdmResource {
	@Autowired
	private AdmService service;

	@Autowired
	private AdmRepository repository;

	@GetMapping
	public ResponseEntity<List<AdmResponseDTO>> findAll() {
		List<Adm> list = service.findAllCached();
		List<AdmResponseDTO> adms = new ArrayList<>();

		for (Adm adm : list) {
			adms.add(new AdmResponseDTO(adm));
		}
		return ResponseEntity.ok().body(adms);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Adm> findById(@PathVariable String id) {
		Adm obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<Adm> insert(@RequestBody @Valid AdmRegisterRequestDTO obj) {
		if (repository.findByEmail(obj.email()) != null)
			return ResponseEntity.badRequest().build();

		String encryptedPassword = new BCryptPasswordEncoder().encode(obj.password());
		Adm user = new Adm(null, obj.name(), obj.phone(), obj.email(), encryptedPassword);

		Adm adm = service.create(user);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(adm.getId()).toUri();
		return ResponseEntity.created(uri).body(adm);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<Adm> patch(@PathVariable String id, @RequestBody Adm obj) {
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