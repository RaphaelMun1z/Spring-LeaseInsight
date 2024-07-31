package com.rm.myadmin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rm.myadmin.entities.User;
import com.rm.myadmin.repositories.UserRepository;

@Service
public class AuthenticationService {
	@Autowired
	private UserRepository<User> userRepository;

	public User authenticate(String email, String password) {
		User user = userRepository.findByEmail(email);
		if (user != null && user.getPassword().equals(password)) {
			return user;
		} else {
			throw new RuntimeException("Invalid email or password");
		}
	}
}
