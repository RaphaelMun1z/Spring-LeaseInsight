package com.rm.leaseinsight.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.leaseinsight.entities.BillingAddress;

public interface BillingAddressRepository extends JpaRepository<BillingAddress, String> {

}
