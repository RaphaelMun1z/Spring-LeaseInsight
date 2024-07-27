package com.rm.myadmin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.myadmin.entities.RentalHistory;

public interface RentalHistoryRepository extends JpaRepository<RentalHistory, Long> {

}
