package com.rm.myadmin.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_additional_feature")
public class AdditionalFeature implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String feature;

	@JsonIgnore
	@OneToMany(mappedBy = "feature")
	private List<ResidenceFeature> residencesFeatures = new ArrayList<>();

	public AdditionalFeature() {

	}

	public AdditionalFeature(Long id, String feature) {
		super();
		this.id = id;
		this.feature = feature;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public List<ResidenceFeature> getResidencesFeatures() {
		return residencesFeatures;
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
		AdditionalFeature other = (AdditionalFeature) obj;
		return Objects.equals(id, other.id);
	}

}
