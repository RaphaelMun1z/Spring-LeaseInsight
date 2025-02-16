package com.rm.leaseinsight.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.leaseinsight.entities.RentalHistory;
import com.rm.leaseinsight.entities.Tenant;

public interface RentalHistoryRepository extends JpaRepository<RentalHistory, String> {
	Set<RentalHistory> findByContractTenant(Tenant tenant);
}
