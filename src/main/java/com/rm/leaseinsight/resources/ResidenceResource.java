package com.rm.leaseinsight.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rm.leaseinsight.dto.ContractDTO;
import com.rm.leaseinsight.dto.ResidenceFeatureDTO;
import com.rm.leaseinsight.dto.ResidenceFeatureRequestDTO;
import com.rm.leaseinsight.dto.ResidenceMinimalResponseDTO;
import com.rm.leaseinsight.dto.ResidenceResponseDTO;
import com.rm.leaseinsight.entities.Contract;
import com.rm.leaseinsight.entities.Residence;
import com.rm.leaseinsight.entities.ResidenceFeature;
import com.rm.leaseinsight.entities.ResidenceImageFile;
import com.rm.leaseinsight.entities.pk.ResidenceFeaturePK;
import com.rm.leaseinsight.services.FileStorageService;
import com.rm.leaseinsight.services.ResidenceService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/residences")
public class ResidenceResource {
	@Autowired
	private ResidenceService service;

	@Autowired
	private FileStorageService fileStorageService;

	private static final Logger logger = LoggerFactory.getLogger(ResidenceResource.class);

	@GetMapping
	public ResponseEntity<List<ResidenceResponseDTO>> findAll() {
		List<Residence> list = service.findAllCached();
		List<ResidenceResponseDTO> residences = new ArrayList<>();

		for (Residence residence : list) {
			residences.add(new ResidenceResponseDTO(residence));
		}
		return ResponseEntity.ok().body(residences);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ResidenceResponseDTO> findById(@PathVariable String id) {
		Residence obj = service.findById(id);
		ResidenceResponseDTO residenceResponse = new ResidenceResponseDTO(obj);
		return ResponseEntity.ok().body(residenceResponse);
	}

	@GetMapping(value = "/occupancy-status/{status}")
	public ResponseEntity<List<ResidenceMinimalResponseDTO>> findByOccupancyStatus(@PathVariable String status) {
		Set<Residence> list = service.findByOccupancyStatus(status);
		List<ResidenceMinimalResponseDTO> residences = new ArrayList<>();

		for (Residence residence : list) {
			residences.add(new ResidenceMinimalResponseDTO(residence));
		}

		return ResponseEntity.ok().body(residences);
	}

	@PostMapping
	public ResponseEntity<ResidenceResponseDTO> insert(@ModelAttribute @Valid Residence obj,
			@RequestParam(name = "images", required = true) MultipartFile[] images) {
		obj = service.create(obj, images);
		ResidenceResponseDTO residence = new ResidenceResponseDTO(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(residence);
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<Residence> patch(@PathVariable String id, @RequestBody Residence obj) {
		obj = service.patch(id, obj);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping(value = "/add-feature")
	public ResponseEntity<ResidenceFeaturePK> addFeature(@RequestBody ResidenceFeatureRequestDTO obj) {
		ResidenceFeature rf = service.addFeature(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(rf.getId()).toUri();
		return ResponseEntity.created(uri).body(rf.getId());
	}

	@GetMapping(value = "/{id}/features")
	public ResponseEntity<Set<ResidenceFeatureDTO>> getFeatures(@PathVariable String id) {
		Set<ResidenceFeatureDTO> features = service.getFeatures(id);
		return ResponseEntity.ok().body(features);
	}

	@Transactional(readOnly = true)
	@GetMapping(value = "/{id}/current-contract")
	public ResponseEntity<ContractDTO> getContract(@PathVariable String id) {
		ContractDTO contractDTO = new ContractDTO(service.getCurrentContract(id));
		return ResponseEntity.ok().body(contractDTO);
	}

	@Transactional(readOnly = true)
	@GetMapping(value = "/{id}/contracts")
	public ResponseEntity<Set<ContractDTO>> getAllContracts(@PathVariable String id) {
		Set<Contract> contracts = service.getAllContracts(id);
		Set<ContractDTO> contractsDTO = new HashSet<>();
		for (Contract contract : contracts) {
			contractsDTO.add(new ContractDTO(contract));
		}
		return ResponseEntity.ok().body(contractsDTO);
	}

	@GetMapping(value = "/{id}/images")
	public ResponseEntity<Set<ResidenceImageFile>> findImages(@PathVariable String id) {
		Set<ResidenceImageFile> obj = service.findImages(id);
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping(value = "/{id}/images/{fileId}")
	public ResponseEntity<Resource> showImage(@PathVariable String id, @PathVariable String fileId,
			HttpServletRequest request) {
		String fileName = service.fileName(id, fileId);
		Resource resource = fileStorageService.loadFileAsResource(fileName);
		String contentType = null;

		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (Exception e) {
			logger.info("Could not determine file type!");
		}

		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> validationExceptionHandler(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((err) -> {
			String fieldName = ((FieldError) err).getField();
			String errorMessage = err.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}