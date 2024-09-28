package com.rm.myadmin.dto;

import java.time.LocalDate;
import java.util.List;

import com.rm.myadmin.entities.File;
import com.rm.myadmin.entities.Report;
import com.rm.myadmin.entities.Residence;

public class ReportRequestDTO {
	private Long id;
	private String description;
	private LocalDate date;
	private Residence residence;
	private List<File> files;

	public ReportRequestDTO() {
	}

	public ReportRequestDTO(Report report, List<File> files) {
		super();
		this.id = report.getId();
		this.description = report.getDescription();
		this.date = report.getDate();
		this.setResidence(report.getResidence());
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

	@Override
	public String toString() {
		return "ReportRequestDTO [id=" + id + ", description=" + description + ", date=" + date + ", residence="
				+ residence + ", files=" + files + "]";
	}

}
