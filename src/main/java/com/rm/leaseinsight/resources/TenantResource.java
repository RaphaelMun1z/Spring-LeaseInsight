package com.rm.leaseinsight.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.rm.leaseinsight.dto.req.TenantPatchRequestDTO;
import com.rm.leaseinsight.dto.req.TenantRequestDTO;
import com.rm.leaseinsight.dto.res.ContractResponseDTO;
import com.rm.leaseinsight.dto.res.RentalHistoryResponseDTO;
import com.rm.leaseinsight.dto.res.ReportResponseDTO;
import com.rm.leaseinsight.dto.res.TenantResponseDTO;
import com.rm.leaseinsight.entities.RentalHistory;
import com.rm.leaseinsight.entities.Report;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.services.ContractService;
import com.rm.leaseinsight.services.RentalHistoryService;
import com.rm.leaseinsight.services.ReportService;
import com.rm.leaseinsight.services.TenantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/tenants")
public class TenantResource {
	@Autowired
	private TenantService service;

	@Autowired
	private ContractService contractService;

	@Autowired
	private RentalHistoryService rentalHistoryService;

	@Autowired
	private ReportService reportService;

	@GetMapping
	public ResponseEntity<List<TenantResponseDTO>> findAll() {
		List<Tenant> list = service.findAllCached();
		List<TenantResponseDTO> tenants = new ArrayList<>();

		for (Tenant tenant : list) {
			tenants.add(new TenantResponseDTO(tenant));
		}
		return ResponseEntity.ok().body(tenants);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<TenantResponseDTO> findById(@PathVariable String id) {
		Tenant obj = service.findById(id);
		TenantResponseDTO tenant = new TenantResponseDTO(obj);
		return ResponseEntity.ok().body(tenant);
	}

	@PostMapping
	public ResponseEntity<TenantResponseDTO> createTenant(@RequestBody @Valid TenantRequestDTO obj) {
		TenantResponseDTO tenant = service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tenant.getId()).toUri();
		return ResponseEntity.created(uri).body(tenant);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<TenantResponseDTO> patch(@PathVariable String id, @RequestBody TenantPatchRequestDTO obj) {
		TenantResponseDTO tenantDTO = service.patch(id, obj);
		return ResponseEntity.ok().body(tenantDTO);
	}

	@GetMapping(value = "/{id}/contracts")
	public ResponseEntity<Set<ContractResponseDTO>> getContracts(@PathVariable String id) {
		return ResponseEntity.ok().body(contractService.findByTenant(id));
	}

	@GetMapping(value = "/{id}/invoices")
	public ResponseEntity<Set<RentalHistoryResponseDTO>> getInvoices(@PathVariable String id) {
		Set<RentalHistory> rentalHistories = rentalHistoryService.findByTenant(id);
		Set<RentalHistoryResponseDTO> list = new HashSet<>();
		for (RentalHistory c : rentalHistories) {
			list.add(new RentalHistoryResponseDTO(c));
		}
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}/reports")
	public ResponseEntity<Set<ReportResponseDTO>> getReports(@PathVariable String id) {
		Set<Report> reports = reportService.findByTenant(id);
		Set<ReportResponseDTO> list = new HashSet<>();
		for (Report c : reports) {
			list.add(new ReportResponseDTO(c));
		}
		return ResponseEntity.ok().body(list);
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