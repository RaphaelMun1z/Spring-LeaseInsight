package com.rm.leaseinsight.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.leaseinsight.entities.Residence;
import com.rm.leaseinsight.entities.ResidenceFeature;
import com.rm.leaseinsight.repositories.ResidenceFeatureRepository;
import com.rm.leaseinsight.services.exceptions.DataViolationException;
import com.rm.leaseinsight.services.exceptions.DatabaseException;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

@Service
public class ResidenceFeatureService {
	@Autowired
	private ResidenceFeatureRepository repository;

	@Autowired
	private ResidenceService residenceService;

	@Transactional
	public ResidenceFeature create(ResidenceFeature obj) {
		try {
			ResidenceFeature rf = repository.save(obj);
			return rf;
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		}
	}

	@Transactional
	public void delete(String residence_id, String feature_id) {
		try {
			Residence residence = residenceService.findById(residence_id);

			if (residence != null) {
				Set<ResidenceFeature> residenceFeatures = residence.getFeatures();

				for (ResidenceFeature rf : residenceFeatures) {
					if (rf.getFeature().getId() == feature_id) {
						repository.delete(rf);
					}
				}
			} else {
				throw new ResourceNotFoundException(residence_id);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(residence_id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
