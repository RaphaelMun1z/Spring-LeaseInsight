package com.rm.myadmin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.myadmin.entities.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

}
