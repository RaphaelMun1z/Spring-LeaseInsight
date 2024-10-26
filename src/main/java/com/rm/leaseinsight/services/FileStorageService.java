package com.rm.leaseinsight.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rm.leaseinsight.config.FileStorageConfig;
import com.rm.leaseinsight.dto.UploadFileResponseDTO;
import com.rm.leaseinsight.services.exceptions.DatabaseException;
import com.rm.leaseinsight.services.exceptions.FileStorageException;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

@Service
public class FileStorageService {
	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(FileStorageConfig fileStorageConfig) {
		this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					e);
		}
	}

	public String storeFile(MultipartFile file) {
		try {
			String fileNameUUID = StringUtils.cleanPath(UUID.randomUUID().toString());
			String originalFilename = file.getOriginalFilename();

			String fileExtension = "";
			if (originalFilename != null && originalFilename.contains(".")) {
				fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
			}

			String fileName = fileNameUUID + fileExtension;

			if (fileName.contains("..")) {
				throw new FileStorageException("Filename contains invalid path sequence.");
			}

			Path targetLocationPath = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocationPath, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		} catch (Exception e) {
			throw new FileStorageException("Could not store file " + file.getOriginalFilename() + ".", e);
		}
	}

	public UploadFileResponseDTO uploadFile(MultipartFile file) {
		try {
			String fileName = storeFile(file);
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/downloadFile/")
					.path(fileName).toUriString();
			return new UploadFileResponseDTO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public List<UploadFileResponseDTO> uploadFiles(MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> this.uploadFile(file)).collect(Collectors.toList());
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new ResourceNotFoundException(fileName);
			}
		} catch (Exception e) {
			throw new ResourceNotFoundException(fileName);
		}
	}
}
