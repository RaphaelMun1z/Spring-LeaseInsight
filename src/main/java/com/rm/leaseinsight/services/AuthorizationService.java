package com.rm.leaseinsight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rm.leaseinsight.entities.User;
import com.rm.leaseinsight.repositories.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {
	@Autowired
	UserRepository<User> repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByEmail(username);
	}
}
