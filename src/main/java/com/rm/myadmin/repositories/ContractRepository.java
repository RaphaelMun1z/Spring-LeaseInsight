package com.rm.myadmin.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.myadmin.entities.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {
	Set<Contract> findByContractStatus(Integer code);
}
