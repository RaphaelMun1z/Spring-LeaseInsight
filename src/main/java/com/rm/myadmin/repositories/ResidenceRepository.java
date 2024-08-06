package com.rm.myadmin.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.myadmin.entities.Owner;
import com.rm.myadmin.entities.Residence;

public interface ResidenceRepository extends JpaRepository<Residence, Long> {
	Set<Residence> findByOwner(Owner owner);
}
