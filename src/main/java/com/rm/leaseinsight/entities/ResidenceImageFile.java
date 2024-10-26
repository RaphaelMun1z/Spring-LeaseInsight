package com.rm.leaseinsight.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_residence_image_file")
public class ResidenceImageFile extends File implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "residence_id")
	private Residence residence;

	public ResidenceImageFile() {
	}

	public ResidenceImageFile(String id, String name, String path, String type, Long size, Residence residence) {
		super(id, name, path, type, size);
		this.residence = residence;
	}

	public Residence getResidence() {
		return residence;
	}

	public void setResidence(Residence residence) {
		this.residence = residence;
	}

}
