package com.rm.leaseinsight.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.leaseinsight.entities.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
	Role findByName(String name);
}
