package com.rm.leaseinsight.dto;

public class UploadFileResponseDTO {
	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private Long size;

	public UploadFileResponseDTO() {
	}

	public UploadFileResponseDTO(String fileName, String fileDownloadUri, String fileType, Long size) {
		this.fileName = fileName;
		this.fileDownloadUri = fileDownloadUri;
		this.fileType = fileType;
		this.size = size;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public String getFileType() {
		return fileType;
	}

	public Long getSize() {
		return size;
	}

}
