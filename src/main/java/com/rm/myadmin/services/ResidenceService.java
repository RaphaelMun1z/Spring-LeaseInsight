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
import com.rm.myadmin.services.exceptions.DataViolationException;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

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
		return obj.orElseThrow(() -> new ResourceNotFoundException("Residence", id));
	}

	@Transactional
	public Residence create(Residence obj) {
		try {
			ResidenceAddress ra = residenceAddressService.findById(obj.getResidenceAddress().getId());
			obj.setResidenceAddress(ra);

			Owner o = ownerService.findById(obj.getOwner().getId());
			obj.setOwner(o);

			Residence residence = repository.save(obj);
			cacheService.putResidenceCache();
			return residence;
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
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
	public Residence patch(String id, Residence obj) {
		try {
			Residence entity = repository.getReferenceById(id);
			patchData(entity, obj);
			Residence r = repository.save(entity);
			cacheService.putResidenceCache();
			return r;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			throw new DatabaseException("Some invalid field.");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getClass());
			return null;
		}
	}

	private void patchData(Residence entity, Residence obj) {
		if (obj.getPropertyType() != null)
			entity.setPropertyType(obj.getPropertyType());
		if (obj.getDescription() != null)
			entity.setDescription(obj.getDescription());
		if (obj.getAptNumber() != null)
			entity.setAptNumber(obj.getAptNumber());
		if (obj.getComplement() != null)
			entity.setComplement(obj.getComplement());
		if (obj.getNumberBedrooms() != 0)
			entity.setNumberBedrooms(obj.getNumberBedrooms());
		if (obj.getNumberBathrooms() != 0)
			entity.setNumberBathrooms(obj.getNumberBathrooms());
		if (obj.getNumberSuites() != 0)
			entity.setNumberSuites(obj.getNumberSuites());
		if (obj.getTotalArea() != 0)
			entity.setTotalArea(obj.getTotalArea());
		if (obj.getBuiltArea() != 0)
			entity.setBuiltArea(obj.getBuiltArea());
		if (obj.getGarageSpaces() != 0)
			entity.setGarageSpaces(obj.getGarageSpaces());
		if (obj.getYearConstruction() != null)
			entity.setYearConstruction(obj.getYearConstruction());
		if (obj.getOccupancyStatus() != null)
			entity.setOccupancyStatus(obj.getOccupancyStatus());
		if (obj.getMarketValue() != null)
			entity.setMarketValue(obj.getMarketValue());
		if (obj.getRentalValue() != null)
			entity.setRentalValue(obj.getRentalValue());
		if (obj.getDateLastRenovation() != null)
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
