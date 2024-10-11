package com.rm.leaseinsight.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.leaseinsight.entities.ResidenceAddress;

public interface ResidenceAddressRepository extends JpaRepository<ResidenceAddress, String> {

}
