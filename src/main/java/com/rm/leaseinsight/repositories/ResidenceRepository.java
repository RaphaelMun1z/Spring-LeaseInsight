package com.rm.leaseinsight.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.leaseinsight.entities.Owner;
import com.rm.leaseinsight.entities.Residence;

public interface ResidenceRepository extends JpaRepository<Residence, String> {
	Set<Residence> findByOwner(Owner owner);

	Set<Residence> findByOccupancyStatus(Integer occupancyStatus);
}
