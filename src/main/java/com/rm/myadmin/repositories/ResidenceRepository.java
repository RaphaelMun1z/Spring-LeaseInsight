package com.rm.myadmin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.myadmin.entities.Residence;

public interface ResidenceRepository extends JpaRepository<Residence, Long> {

}
