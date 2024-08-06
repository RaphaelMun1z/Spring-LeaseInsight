package com.rm.myadmin.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.myadmin.entities.Contract;
import com.rm.myadmin.entities.Tenant;

public interface ContractRepository extends JpaRepository<Contract, Long> {
	Set<Contract> findByContractStatus(Integer code);

	Set<Contract> findByTenant(Tenant tenant);
}
