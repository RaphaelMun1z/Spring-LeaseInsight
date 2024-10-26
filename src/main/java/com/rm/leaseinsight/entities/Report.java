package com.rm.leaseinsight.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.rm.leaseinsight.entities.enums.ReportType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_reports", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "description", "residence_id", "tenant_id" }) })
public class Report implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@NotNull(message = "Required field")
	private String description;

	@NotNull(message = "Required field")
	private LocalDate date;

	@NotNull(message = "Required field")
	private Integer reportType;

	@NotNull(message = "Required field")
	@ManyToOne
	@JoinColumn(name = "residence_id")
	private Residence residence;

	@NotNull(message = "Required field")
	@ManyToOne
	@JoinColumn(name = "tenant_id")
	private Tenant tenant;

	@NotNull(message = "Required field")
	@OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
	private Set<ReportFile> files = new HashSet<>();

	public Report() {
	}

	public Report(String id, String description, ReportType reportType, Residence residence, Tenant tenant) {
		super();
		this.id = id;
		this.description = description;
		this.date = LocalDate.now();
		setReportType(reportType);
		this.residence = residence;
		this.tenant = tenant;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Residence getResidence() {
		return residence;
	}

	public void setResidence(Residence residence) {
		this.residence = residence;
	}

	public Set<ReportFile> getFiles() {
		return files;
	}

	public void addFile(ReportFile file) {
		files.add(file);
	}

	public Tenant getTenant() {
		return tenant;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Report other = (Report) obj;
		return Objects.equals(id, other.id);
	}

}
