package com.rm.myadmin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rm.myadmin.entities.Contract;
import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.repositories.ContractRepository;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

@Service
public class ContractService {
	@Autowired
	private ContractRepository repository;

	@Autowired
	private ResidenceService residenceService;

	@Autowired
	private TenantService tenantService;

	public List<Contract> findAll() {
		return repository.findAll();
	}

	public Contract findById(Long id) {
		Optional<Contract> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Contract create(Contract obj) {
		Residence r = residenceService.findById(obj.getResidence().getId());
		obj.setResidence(r);
		r.setContract(obj);
		obj.setTenant(tenantService.findById(obj.getTenant().getId()));
		return repository.save(obj);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
