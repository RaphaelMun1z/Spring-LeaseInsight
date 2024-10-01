package com.rm.myadmin.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.myadmin.dto.ContractDTO;
import com.rm.myadmin.dto.ResidenceFeatureDTO;
import com.rm.myadmin.entities.AdditionalFeature;
import com.rm.myadmin.entities.Owner;
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

	@Lazy
	@Autowired
	private ResidenceFeatureService residenceFeatureService;

	@Lazy
	@Autowired
	private OwnerService ownerService;

	@Autowired
	private CacheService cacheService;

	@Cacheable("findAllResidence")
	public List<Residence> findAllCached() {
		return findAll();
	}

	public List<Residence> findAll() {
		return repository.findAll();
	}

	public Residence findById(String id) {
		Optional<Residence> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public Residence create(Residence obj) {
		try {
			ResidenceAddress ra = residenceAddressService.findById(obj.getResidenceAddress().getId());
			obj.setResidenceAddress(ra);
			Residence residence = repository.save(obj);
			cacheService.putResidenceCache();
			return residence;
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			return null;
		}
	}

	@Transactional
	public void delete(String id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				cacheService.evictAllCacheValues("findAllResidence");
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
	public Residence update(String id, Residence obj) {
		try {
			Residence entity = repository.getReferenceById(id);
			updateData(entity, obj);
			Residence r = repository.save(entity);
			cacheService.putResidenceCache();
			return r;
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

	@Transactional
	public ResidenceFeature addFeature(ResidenceFeature obj) {
		Residence r = this.findById(obj.getProperty().getId());
		AdditionalFeature af = additionalFeatureService.findById(obj.getFeature().getId());
		obj.setProperty(r);
		obj.setFeature(af);
		return residenceFeatureService.create(obj);
	}

	public Set<ResidenceFeatureDTO> getFeatures(String id) {
		Residence r = this.findById(id);
		Set<ResidenceFeatureDTO> fDTO = new HashSet<>();
		for (ResidenceFeature residenceFeature : r.getFeatures()) {
			fDTO.add(new ResidenceFeatureDTO(residenceFeature));
		}
		return fDTO;
	}

	public ContractDTO getCurrentContract(String id) {
		Residence r = this.findById(id);
		ContractDTO cDTO = new ContractDTO(r.getContract());
		return cDTO;
	}

	public Set<Residence> findByOwner(String id) {
		Owner owner = ownerService.findById(id);
		return repository.findByOwner(owner);
	}

}
