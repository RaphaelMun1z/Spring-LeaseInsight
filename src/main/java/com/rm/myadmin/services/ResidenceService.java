package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rm.myadmin.dto.ContractDTO;
import com.rm.myadmin.entities.AdditionalFeature;
import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.entities.ResidenceAddress;
import com.rm.myadmin.entities.ResidenceFeature;
import com.rm.myadmin.repositories.ResidenceRepository;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ResidenceService {
	@Autowired
	private ResidenceRepository repository;

	@Autowired
	private ResidenceAddressService residenceAddressService;

	@Autowired
	private AdditionalFeatureService additionalFeatureService;

	@Autowired
	private ResidenceFeatureService residenceFeatureService;

	public List<Residence> findAll() {
		return repository.findAll();
	}

	public Residence findById(Long id) {
		Optional<Residence> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Residence create(Residence obj) {
		ResidenceAddress ra = residenceAddressService.findById(obj.getResidenceAddress().getId());
		obj.setResidenceAddress(ra);
		return repository.save(obj);
	}

	public void delete(Long id) {
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

	public Residence update(Long id, Residence obj) {
		try {
			Residence entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Residence entity, Residence obj) {
		entity.setPropertyType(obj.getPropertyType());
		entity.setDescription(obj.getDescription());
		entity.setAptNumber(obj.getAptNumber());
		entity.setComplement(obj.getComplement());
		entity.setNumberBedrooms(obj.getNumberBedrooms());
		entity.setNumberBathrooms(obj.getNumberBathrooms());
		entity.setNumberSuites(obj.getNumberSuites());
		entity.setTotalArea(obj.getTotalArea());
		entity.setBuiltArea(obj.getBuiltArea());
		entity.setGarageSpaces(obj.getGarageSpaces());
		entity.setYearConstruction(obj.getYearConstruction());
		entity.setOccupancyStatus(obj.getOccupancyStatus());
		entity.setMarketValue(obj.getMarketValue());
		entity.setRentalValue(obj.getRentalValue());
		entity.setDateLastRenovation(obj.getDateLastRenovation());
	}

	public ResidenceFeature addFeature(ResidenceFeature obj) {
		Residence r = this.findById(obj.getProperty().getId());
		AdditionalFeature af = additionalFeatureService.findById(obj.getFeature().getId());
		obj.setProperty(r);
		obj.setFeature(af);
		return residenceFeatureService.create(obj);
	}

	public Set<ResidenceFeature> getFeatures(Long id) {
		Residence r = this.findById(id);
		return r.getFeatures();
	}

	public ContractDTO getCurrentContract(Long id) {
		Residence r = this.findById(id);
		ContractDTO cDTO = new ContractDTO(r.getContract());
		return cDTO;
	}

}
