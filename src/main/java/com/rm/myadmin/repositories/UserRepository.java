package com.rm.myadmin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.myadmin.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
