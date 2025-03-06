package com.rm.leaseinsight.dto.res;

import java.time.LocalDate;

import com.rm.leaseinsight.entities.Report;
import com.rm.leaseinsight.entities.Residence;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.entities.enums.ReportType;

public class ReportResponseDTO {
	private String id;
	private String description;
	private LocalDate date;
	private Integer reportType;
	private Residence residence;
	private Tenant tenant;

	public ReportResponseDTO() {
	}

	public ReportResponseDTO(Report report) {
		super();
		this.id = report.getId();
		this.description = report.getDescription();
		this.date = report.getDate();
		setReportType(report.getReportType());
		this.residence = report.getResidence();
		this.tenant = report.getTenant();
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDate() {
		return date;
	}

	public ReportType getReportType() {
		return ReportType.valueOf(reportType);
	}

	public void setReportType(ReportType reportType) {
		if (reportType != null) {
			this.reportType = reportType.getCode();
		}
	}

	public Residence getResidence() {
		return residence;
	}

	public Tenant getTenant() {
		return tenant;
	}

}