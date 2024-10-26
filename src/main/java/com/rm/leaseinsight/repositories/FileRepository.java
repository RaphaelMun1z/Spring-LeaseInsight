package com.rm.leaseinsight.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.leaseinsight.entities.File;

public interface FileRepository<T extends File> extends JpaRepository<T, String> {
}
