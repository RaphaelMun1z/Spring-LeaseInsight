package com.rm.myadmin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.myadmin.entities.File;

public interface FileRepository extends JpaRepository<File, String> {
}
