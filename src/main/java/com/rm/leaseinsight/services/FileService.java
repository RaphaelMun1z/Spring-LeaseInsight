package com.rm.leaseinsight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.leaseinsight.entities.File;
import com.rm.leaseinsight.entities.Report;
import com.rm.leaseinsight.repositories.FileRepository;
import com.rm.leaseinsight.services.exceptions.DatabaseException;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

@Service
public class FileService {
	@Autowired
	private FileRepository repository;

	@Lazy
	@Autowired
	private ReportService reportService;

	@Transactional
	public File create(Report report, File obj) {
		obj.setReport(report);
		File file = repository.save(obj);
		return file;
	}

	@Transactional
	public void delete(String id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
			} else {
				throw new ResourceNotFoundException(id);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
