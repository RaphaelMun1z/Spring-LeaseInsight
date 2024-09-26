package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.myadmin.entities.Report;
import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.repositories.ReportRepository;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReportService {
	@Autowired
	private ReportRepository repository;

	@Autowired
	private ResidenceService residenceService;

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

	@Transactional
	public Report create(Report obj) {
		Residence r = residenceService.findById(obj.getResidence().getId());
		obj.setResidence(r);
		Report report = repository.save(obj);
		cacheService.putReportCache();
		return report;
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
