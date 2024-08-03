package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.repositories.ResidenceRepository;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ResidenceService {
	@Autowired
	private ResidenceRepository repository;

	public List<Residence> findAll() {
		return repository.findAll();
	}

	public Residence findById(Long id) {
		Optional<Residence> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Residence create(Residence obj) {
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

}
