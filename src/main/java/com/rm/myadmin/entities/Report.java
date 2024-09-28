package com.rm.myadmin.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_reports")
public class Report implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Required field")
	private String description;

	@NotNull(message = "Required field")
	private LocalDate date;

	@NotNull(message = "Required field")
	@ManyToOne
	@JoinColumn(name = "residence_id")
	private Residence residence;

	@NotNull(message = "Required field")
	@OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
	private Set<File> files = new HashSet<>();

	public Report() {
	}

	public Report(Long id, String description, Residence residence) {
		super();
		this.id = id;
		this.description = description;
		this.date = LocalDate.now();
		this.residence = residence;
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

	public Residence getResidence() {
		return residence;
	}

	public void setResidence(Residence residence) {
		this.residence = residence;
	}

	public Set<File> getFiles() {
		return files;
	}

	public void addFile(File file) {
		files.add(file);
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

	@Override
	public String toString() {
		return "Report [id=" + id + ", description=" + description + ", date=" + date + ", residence=" + residence
				+ ", files=" + files + "]";
	}

}
