package com.rm.leaseinsight.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.leaseinsight.entities.Contract;
import com.rm.leaseinsight.entities.Tenant;

public interface ContractRepository extends JpaRepository<Contract, String> {
	Set<Contract> findByContractStatus(Integer code);

	Set<Contract> findByTenant(Tenant tenant);
}
