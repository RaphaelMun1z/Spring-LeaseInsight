package com.rm.myadmin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.myadmin.entities.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {

}
