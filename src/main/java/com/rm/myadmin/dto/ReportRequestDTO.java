package com.rm.myadmin.dto;

import java.time.LocalDate;
import java.util.List;

import com.rm.myadmin.entities.File;
import com.rm.myadmin.entities.Report;
import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.entities.Tenant;
import com.rm.myadmin.entities.enums.ReportType;

public class ReportRequestDTO {
	private Long id;
	private String description;
	private LocalDate date;
	private Integer reportType;
	private Residence residence;
	private Tenant tenant;
	private List<File> files;

	public ReportRequestDTO() {
	}

	public ReportRequestDTO(Report report, List<File> files) {
		super();
		this.id = report.getId();
		this.description = report.getDescription();
		this.date = report.getDate();
		setReportType(report.getReportType());
		this.residence = report.getResidence();
		this.tenant = report.getTenant();
		this.files = files;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public ReportType getReportType() {
		return ReportType.valueOf(reportType);
	}

	public void setReportType(ReportType reportType) {
		if (reportType != null) {
			this.reportType = reportType.getCode();
		}
	}

	public List<File> getFiles() {
		return files;
	}

	public void addFile(File file) {
		files.add(file);
	}

	public Residence getResidence() {
		return residence;
	}

	public void setResidence(Residence residence) {
		this.residence = residence;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

}
