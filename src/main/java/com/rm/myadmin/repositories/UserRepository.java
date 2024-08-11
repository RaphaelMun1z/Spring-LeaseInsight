package com.rm.myadmin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.rm.myadmin.entities.User;

public interface UserRepository<T extends User> extends JpaRepository<T, String> {
	UserDetails findByEmail(String email);
}