package com.rm.leaseinsight.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.leaseinsight.entities.Report;
import com.rm.leaseinsight.entities.Tenant;

public interface ReportRepository extends JpaRepository<Report, String> {
	Set<Report> findByTenant(Tenant tenant);
}