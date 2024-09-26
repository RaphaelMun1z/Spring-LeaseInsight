package com.rm.myadmin.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rm.myadmin.config.FileStorageConfig;
import com.rm.myadmin.services.exceptions.FileStorageException;
import com.rm.myadmin.services.exceptions.ResourceNotFoundException;

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
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Filename contains invalid path sequence.");
			}

			Path targetLocationPath = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocationPath, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (Exception e) {
			throw new FileStorageException("Could not store file " + fileName + ".", e);
		}
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
