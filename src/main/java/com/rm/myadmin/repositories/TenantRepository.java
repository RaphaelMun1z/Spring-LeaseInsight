package com.rm.myadmin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.myadmin.entities.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

}
