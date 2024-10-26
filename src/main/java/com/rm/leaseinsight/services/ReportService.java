package com.rm.leaseinsight.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rm.leaseinsight.dto.ReportRequestDTO;
import com.rm.leaseinsight.dto.ReportResponseDTO;
import com.rm.leaseinsight.dto.UploadFileResponseDTO;
import com.rm.leaseinsight.entities.Report;
import com.rm.leaseinsight.entities.ReportFile;
import com.rm.leaseinsight.entities.Residence;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.repositories.ReportRepository;
import com.rm.leaseinsight.services.exceptions.DataViolationException;
import com.rm.leaseinsight.services.exceptions.DatabaseException;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Service
public class ReportService {
	@Autowired
	private ReportRepository repository;

	@Autowired
	private TenantService tenantService;

	@Autowired
	private ResidenceService residenceService;

	@Autowired
	private FileService fileService;

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private CacheService cacheService;

	@Cacheable("findAllReport")
	public List<Report> findAllCached() {
		return findAll();
	}

	public List<Report> findAll() {
		return repository.findAll();
	}

	public Report findById(String id) {
		Optional<Report> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Report", id));
	}

	public Set<ReportFile> findFiles(String id) {
		Report r = this.findById(id);
		return r.getFiles();
	}

	public String fileName(String reportId, String fileId) {
		Report report = this.findById(reportId);
		ReportFile f = report.getFiles().stream().filter(file -> file.getId().equals(fileId)).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException(fileId));
		String fileName = f.getName();
		return fileName;
	}

	@Transactional(rollbackFor = { Exception.class, ResourceNotFoundException.class })
	public ReportResponseDTO create(ReportRequestDTO obj, MultipartFile[] files) {
		try {
			Tenant tenant = tenantService.findById(obj.getTenant());
			Residence residence = residenceService.findById(obj.getResidence());
			Report report = new Report(null, obj.getDescription(), obj.getReportType(), residence, tenant);
			repository.save(report);

			List<UploadFileResponseDTO> uploadedFiles = fileStorageService.uploadFiles(files);
			for (UploadFileResponseDTO file : uploadedFiles) {
				ReportFile f = new ReportFile(null, file.getFileName(), file.getFileDownloadUri(), file.getFileType(),
						file.getSize(), report);
				fileService.createReportFile(report, f);
			}

			cacheService.putReportCache();
			return new ReportResponseDTO(report);
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		}
	}

	@Transactional
	public void delete(String id) {
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
	public Report patch(String id, Report obj) {
		try {
			Report entity = repository.getReferenceById(id);
			patchData(entity, obj);
			Report r = repository.save(entity);
			cacheService.putReportCache();
			return r;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	private void patchData(Report entity, Report obj) {
		if (obj.getDescription() != null)
			entity.setDescription(obj.getDescription());
	}
}
