package com.rm.leaseinsight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.leaseinsight.entities.File;
import com.rm.leaseinsight.entities.Report;
import com.rm.leaseinsight.entities.ReportFile;
import com.rm.leaseinsight.entities.Residence;
import com.rm.leaseinsight.entities.ResidenceImageFile;
import com.rm.leaseinsight.repositories.ReportFileRepository;
import com.rm.leaseinsight.repositories.ResidenceImageFileRepository;
import com.rm.leaseinsight.services.exceptions.DatabaseException;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

@Service
public class FileService {
	@Autowired
	private ReportFileRepository reportFileRepository;

	@Autowired
	private ResidenceImageFileRepository residenceImageFileRepository;

	@Lazy
	@Autowired
	private ReportService reportService;

	@Transactional
	public File createReportFile(Report report, ReportFile obj) {
		obj.setReport(report);
		File file = reportFileRepository.save(obj);
		return file;
	}

	@Transactional
	public File createResidenceImageFile(Residence residence, ResidenceImageFile obj) {
		obj.setResidence(residence);
		File file = residenceImageFileRepository.save(obj);
		return file;
	}

	@Transactional
	public void deleteReportFile(String id) {
		try {
			if (reportFileRepository.existsById(id)) {
				reportFileRepository.deleteById(id);
			} else {
				throw new ResourceNotFoundException(id);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	@Transactional
	public void deleteResidenceImageFile(String id) {
		try {
			if (residenceImageFileRepository.existsById(id)) {
				residenceImageFileRepository.deleteById(id);
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
