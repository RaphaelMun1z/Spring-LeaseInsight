package com.rm.leaseinsight.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rm.leaseinsight.dto.LoginRequestDTO;
import com.rm.leaseinsight.dto.LoginResponseDTO;
import com.rm.leaseinsight.services.AuthorizationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationResource {
	@Autowired
	private AuthorizationService service;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
		return ResponseEntity.ok(service.login(data));
	}
}