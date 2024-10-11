package com.rm.leaseinsight.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rm.leaseinsight.dto.LoginRequestDTO;
import com.rm.leaseinsight.dto.LoginResponseDTO;
import com.rm.leaseinsight.entities.User;
import com.rm.leaseinsight.infra.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationResource {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);

		var token = tokenService.generateToken((User) auth.getPrincipal());

		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
}