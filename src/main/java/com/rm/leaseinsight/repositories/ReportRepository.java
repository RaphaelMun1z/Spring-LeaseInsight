package com.rm.leaseinsight.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.leaseinsight.entities.Report;

public interface ReportRepository extends JpaRepository<Report, String> {
}