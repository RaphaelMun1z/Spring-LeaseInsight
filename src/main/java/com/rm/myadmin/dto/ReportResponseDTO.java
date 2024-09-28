package com.rm.myadmin.dto;

import java.time.LocalDate;

import com.rm.myadmin.entities.Report;
import com.rm.myadmin.entities.Residence;

public class ReportResponseDTO {
	private Long id;
	private String description;
	private LocalDate date;
	private Residence residence;

	public ReportResponseDTO() {
	}

	public ReportResponseDTO(Report report) {
		super();
		this.id = report.getId();
		this.description = report.getDescription();
		this.date = report.getDate();
		this.residence = report.getResidence();
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDate() {
		return date;
	}

	public Residence getResidence() {
		return residence;
	}
}