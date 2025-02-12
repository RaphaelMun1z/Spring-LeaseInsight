package com.rm.leaseinsight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rm.leaseinsight.dto.LoginRequestDTO;
import com.rm.leaseinsight.dto.LoginResponseDTO;
import com.rm.leaseinsight.dto.TokenDTO;
import com.rm.leaseinsight.entities.User;
import com.rm.leaseinsight.infra.security.TokenService;
import com.rm.leaseinsight.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthorizationService implements UserDetailsService {
	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	UserRepository<User> repository;
	
	@Transactional
	public LoginResponseDTO login(LoginRequestDTO data) {
		try {
			var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
			var auth = this.authenticationManager.authenticate(usernamePassword);
			TokenDTO token = tokenService.generateToken((User) auth.getPrincipal());
			return new LoginResponseDTO(token);
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByEmail(username);
	}
}
