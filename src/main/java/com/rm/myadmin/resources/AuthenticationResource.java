package com.rm.myadmin.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rm.myadmin.dto.LoginRequestDTO;
import com.rm.myadmin.dto.LoginResponseDTO;
import com.rm.myadmin.dto.RegisterRequestDTO;
import com.rm.myadmin.entities.Adm;
import com.rm.myadmin.entities.User;
import com.rm.myadmin.infra.security.TokenService;
import com.rm.myadmin.repositories.AdmRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationResource {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AdmRepository repository;

	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);

		var token = tokenService.generateToken((User) auth.getPrincipal());

		return ResponseEntity.ok(new LoginResponseDTO(token));
	}

	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Valid RegisterRequestDTO data) {
		if (this.repository.findByEmail(data.email()) != null)
			return ResponseEntity.badRequest().build();

		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		Adm user = new Adm(null, data.name(), data.phone(), data.email(), encryptedPassword);

		this.repository.save(user);

		return ResponseEntity.ok().build();
	}
}