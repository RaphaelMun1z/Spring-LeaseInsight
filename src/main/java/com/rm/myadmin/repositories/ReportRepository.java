package com.rm.myadmin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.myadmin.entities.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}