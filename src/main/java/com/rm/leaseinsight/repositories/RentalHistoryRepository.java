package com.rm.leaseinsight.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.leaseinsight.entities.RentalHistory;

public interface RentalHistoryRepository extends JpaRepository<RentalHistory, String> {

}
