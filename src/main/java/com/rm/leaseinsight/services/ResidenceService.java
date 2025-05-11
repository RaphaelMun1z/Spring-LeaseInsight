package com.rm.leaseinsight.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rm.leaseinsight.dto.ResidenceFeatureDTO;
import com.rm.leaseinsight.dto.req.ResidenceFeatureRequestDTO;
import com.rm.leaseinsight.dto.req.ResidenceFilterDTO;
import com.rm.leaseinsight.dto.req.ResidenceRequestDTO;
import com.rm.leaseinsight.dto.res.ContractResponseDTO;
import com.rm.leaseinsight.dto.res.ResidenceResponseDTO;
import com.rm.leaseinsight.dto.res.UploadFileResponseDTO;
import com.rm.leaseinsight.entities.AdditionalFeature;
import com.rm.leaseinsight.entities.Contract;
import com.rm.leaseinsight.entities.FieldViolationMessage;
import com.rm.leaseinsight.entities.Owner;
import com.rm.leaseinsight.entities.Residence;
import com.rm.leaseinsight.entities.ResidenceAddress;
import com.rm.leaseinsight.entities.ResidenceFeature;
import com.rm.leaseinsight.entities.ResidenceImageFile;
import com.rm.leaseinsight.entities.enums.OccupancyStatus;
import com.rm.leaseinsight.mapper.Mapper;
import com.rm.leaseinsight.repositories.ResidenceRepository;
import com.rm.leaseinsight.resources.ContractResource;
import com.rm.leaseinsight.resources.ResidenceResource;
import com.rm.leaseinsight.services.exceptions.DataViolationException;
import com.rm.leaseinsight.services.exceptions.DatabaseException;
import com.rm.leaseinsight.services.exceptions.FieldViolationException;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

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
	private FileService fileService;

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private CacheService cacheService;

	@Autowired
	PagedResourcesAssembler<ResidenceResponseDTO> assembler;

	@Cacheable("findAllResidence")
	public List<Residence> findAllCached() {
		return findAll();
	}

	public List<Residence> findAll() {
		return repository.findAll();
	}

	public PagedModel<EntityModel<ResidenceResponseDTO>> findDynamic(ResidenceFilterDTO filter, Pageable pageable) {
		Page<Residence> residencePage = repository.findAll(filter.toSpec(), pageable);

		Page<ResidenceResponseDTO> dtoPage = residencePage.map(residence -> {
			ResidenceResponseDTO dto = new ResidenceResponseDTO(residence);
			dto.add(linkTo(methodOn(ResidenceResource.class).findById(dto.getId())).withSelfRel());
			return dto;
		});

		Link selfLink = linkTo(methodOn(ResidenceResource.class).findDynamic(filter, pageable)).withSelfRel();

		return assembler.toModel(dtoPage, selfLink);
	}

	public Set<Residence> findByOccupancyStatus(String status) {
		try {
			return repository.findByOccupancyStatus(OccupancyStatus.valueOf(status.toUpperCase()).getCode());
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException();
		}
	}

	public Residence findById(String id) {
		Optional<Residence> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Residence", id));
	}

	public Set<ResidenceImageFile> findImages(String id) {
		Residence r = this.findById(id);
		return r.getFiles();
	}

	public String fileName(String residenceId, String fileId) {
		Residence residence = this.findById(residenceId);
		ResidenceImageFile f = residence.getFiles().stream().filter(file -> file.getId().equals(fileId)).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException(fileId));
		String fileName = f.getName();
		return fileName;
	}

	@Transactional(rollbackFor = { Exception.class, ResourceNotFoundException.class })
	public Residence create(Residence obj, MultipartFile[] files) {
		try {
			ResidenceAddress ra = residenceAddressService.findById(obj.getResidenceAddress().getId());
			obj.setResidenceAddress(ra);

			Owner o = ownerService.findById(obj.getOwner().getId());
			obj.setOwner(o);

			Residence residence = repository.save(obj);

			if (files != null) {
				List<UploadFileResponseDTO> uploadedFiles = fileStorageService.uploadFiles(files);
				for (UploadFileResponseDTO file : uploadedFiles) {
					ResidenceImageFile f = new ResidenceImageFile(null, file.getFileName(), file.getFileDownloadUri(),
							file.getFileType(), file.getSize(), residence);
					fileService.createResidenceImageFile(residence, f);
				}
			}

			cacheService.putResidenceCache();
			return residence;
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	@Transactional
	public ResidenceFeature addFeature(ResidenceFeatureRequestDTO obj) {
		try {
			Residence residence = this.findById(obj.getPropertyId());
			AdditionalFeature additionalFeature = additionalFeatureService.findById(obj.getFeatureId());
			ResidenceFeature residenceFeature = new ResidenceFeature(residence, additionalFeature);
			return residenceFeatureService.create(residenceFeature);
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public Set<ResidenceFeatureDTO> getFeatures(String id) {
		Residence r = this.findById(id);
		Set<ResidenceFeatureDTO> fDTO = new HashSet<>();
		for (ResidenceFeature residenceFeature : r.getFeatures()) {
			fDTO.add(new ResidenceFeatureDTO(residenceFeature));
		}
		return fDTO;
	}

	public ContractResponseDTO getCurrentContract(String id) {
		try {
			Residence residence = this.findById(id);
			Contract contract = residence.getActiveContract();
			if (contract == null)
				new ResourceNotFoundException("Current Contract", id);

			ContractResponseDTO contractDTO = Mapper.modelMapper(contract, ContractResponseDTO.class);
			contractDTO.add(linkTo(methodOn(ContractResource.class).findById(id)).withSelfRel());
			return contractDTO;
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	public Set<ContractResponseDTO> getAllContracts(String id) {
		try {
			Residence residence = this.findById(id);
			Set<Contract> contracts = residence.getContracts();
			Set<ContractResponseDTO> contractsResponse = contracts.stream()
					.map(contract -> Mapper.modelMapper(contract, ContractResponseDTO.class))
					.collect(Collectors.toSet());
			contractsResponse.forEach(contract -> contract
					.add(linkTo(methodOn(ContractResource.class).findById(contract.getId())).withSelfRel()));
			return contractsResponse;
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}

	public Set<Residence> findByOwner(String id) {
		Owner owner = ownerService.findById(id);
		return repository.findByOwner(owner);
	}

	@Transactional
	public void delete(String id) {
		try {
			if (!repository.existsById(id)) {
				throw new ResourceNotFoundException(id);
			}

			Residence r = this.findById(id);
			int qntContracts = r.getContracts().size();
			int qntReports = r.getReports().size();

			if (qntContracts > 0) {
				throw new DatabaseException("Erro de integridade referencial: a residência está vinculada a "
						+ qntContracts + " contrato(s).");
			}

			if (qntReports > 0) {
				throw new DatabaseException(
						"Erro de integridade referencial: a residência está vinculada a " + qntReports + " relato(s).");
			}

			repository.deleteById(id);
			cacheService.evictAllCacheValues("findAllResidence");
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DatabaseException e) {
			throw new DatabaseException(e.getMessage());
		} catch (Exception e) {
			throw new DatabaseException("Erro: " + e.getMessage());
		}
	}

	@Transactional
	public Residence patch(String id, ResidenceRequestDTO obj) {
		try {
			Residence entity = repository.getReferenceById(id);
			patchData(entity, obj);
			Residence r = repository.save(entity);
			cacheService.putResidenceCache();
			return r;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (ConstraintViolationException e) {
			List<FieldViolationMessage> errors = e.getConstraintViolations().stream()
					.map(violation -> new FieldViolationMessage(violation.getPropertyPath().toString(),
							violation.getMessage()))
					.toList();
			throw new FieldViolationException(errors);
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException();
		} catch (HttpMessageNotReadableException e) {
			throw new DataViolationException();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	private void patchData(Residence entity, ResidenceRequestDTO obj) {
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
}
