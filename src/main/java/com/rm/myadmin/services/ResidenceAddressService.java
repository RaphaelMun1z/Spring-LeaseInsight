package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rm.myadmin.entities.ResidenceAddress;
import com.rm.myadmin.repositories.ResidenceAddressRepository;
import com.rm.myadmin.services.exceptions.DatabaseException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ResidenceAddressService {
	@Autowired
	private ResidenceAddressRepository repository;

	public List<ResidenceAddress> findAll() {
		return repository.findAll();
	}

	public ResidenceAddress findById(Long id) {
		Optional<ResidenceAddress> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public ResidenceAddress create(ResidenceAddress obj) {
		return repository.save(obj);
	}

	@Transactional
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

	@Transactional
	public ResidenceAddress update(Long id, ResidenceAddress obj) {
		try {
			ResidenceAddress entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(ResidenceAddress entity, ResidenceAddress obj) {
		entity.setNumber(obj.getNumber());
		entity.setStreet(obj.getStreet());
		entity.setDistrict(obj.getDistrict());
		entity.setCity(obj.getCity());
		entity.setState(obj.getState());
		entity.setCountry(obj.getCountry());
		entity.setCep(obj.getCep());
		entity.setComplement(obj.getComplement());
	}
}
