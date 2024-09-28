package com.rm.myadmin.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rm.myadmin.dto.ReportRequestDTO;
import com.rm.myadmin.dto.ReportResponseDTO;
import com.rm.myadmin.dto.UploadFileResponseDTO;
import com.rm.myadmin.entities.File;
import com.rm.myadmin.entities.Report;
import com.rm.myadmin.services.FileStorageService;
import com.rm.myadmin.services.ReportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/reports")
public class ReportResource {
	@Autowired
	private ReportService service;

	@Autowired
	private FileStorageService fileStorageService;

	@GetMapping
	public ResponseEntity<List<ReportResponseDTO>> findAll() {
		List<Report> list = service.findAllCached();
		List<ReportResponseDTO> reports = new ArrayList<>();

		for (Report report : list) {
			reports.add(new ReportResponseDTO(report));
		}
		return ResponseEntity.ok().body(reports);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Report> findById(@PathVariable Long id) {
		Report obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<ReportResponseDTO> insert(@ModelAttribute @Valid ReportRequestDTO obj,
			@RequestParam("files") MultipartFile[] files) {
		List<UploadFileResponseDTO> uploadedFiles = Arrays.asList(files).stream()
				.map(file -> fileStorageService.uploadFile(file)).collect(Collectors.toList());
		ReportResponseDTO report = service.create(obj, uploadedFiles);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(report);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Report> update(@PathVariable Long id, @RequestBody Report obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping(value = "/{id}/files")
	public ResponseEntity<Set<File>> findFiles(@PathVariable Long id) {
		Set<File> obj = service.findFiles(id);
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