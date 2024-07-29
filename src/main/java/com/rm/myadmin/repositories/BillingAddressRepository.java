package com.rm.myadmin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.myadmin.entities.BillingAddress;

public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long> {

}
