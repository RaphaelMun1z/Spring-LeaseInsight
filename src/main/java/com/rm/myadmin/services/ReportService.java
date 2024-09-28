package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.myadmin.dto.ReportRequestDTO;
import com.rm.myadmin.dto.ReportResponseDTO;
import com.rm.myadmin.dto.UploadFileResponseDTO;
import com.rm.myadmin.entities.File;
import com.rm.myadmin.entities.Report;
import com.rm.myadmin.repositories.ReportRepository;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReportService {
	@Autowired
	private ReportRepository repository;

	@Autowired
	private FileService fileService;

	@Autowired
	private CacheService cacheService;

	@Cacheable("findAllReport")
	public List<Report> findAllCached() {
		return findAll();
	}

	public List<Report> findAll() {
		return repository.findAll();
	}

	public Report findById(Long id) {
		Optional<Report> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Set<File> findFiles(Long id) {
		Report r = this.findById(id);
		return r.getFiles();
	}

	public String fileName(Long reportId, Long fileId) {
		Report report = this.findById(reportId);
		File f = report.getFiles().stream().filter(file -> file.getId().equals(fileId)).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException(fileId));
		String fileName = f.getName();
		return fileName;
	}

	@Transactional
	public ReportResponseDTO create(ReportRequestDTO obj, List<UploadFileResponseDTO> uploadedFiles) {
		Report report = new Report(null, obj.getDescription(), obj.getResidence());
		for (UploadFileResponseDTO file : uploadedFiles) {
			File f = new File(file.getFileName(), file.getFileDownloadUri(), file.getFileType(), file.getSize(),
					report);
			fileService.create(report, f);
			report.addFile(f);
		}
		repository.save(report);
		cacheService.putReportCache();
		return new ReportResponseDTO(report);
	}

	@Transactional
	public void delete(Long id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				cacheService.evictAllCacheValues("findAllReport");
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
	public Report update(Long id, Report obj) {
		try {
			Report entity = repository.getReferenceById(id);
			updateData(entity, obj);
			Report r = repository.save(entity);
			cacheService.putReportCache();
			return r;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Report entity, Report obj) {
		entity.setDescription(obj.getDescription());
	}
}
