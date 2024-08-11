package com.rm.myadmin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.myadmin.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
