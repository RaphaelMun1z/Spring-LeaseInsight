package com.rm.leaseinsight.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.leaseinsight.entities.AdditionalFeature;

public interface AdditionalFeatureRepository extends JpaRepository<AdditionalFeature, String> {

}
