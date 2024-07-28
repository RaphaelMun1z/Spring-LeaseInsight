package com.rm.myadmin.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_residence_feature")
public class ResidenceFeature implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "residence_id")
	private Residence property;

	@ManyToOne
	@JoinColumn(name = "feature_id")
	private AdditionalFeature feature;

	public ResidenceFeature() {

	}

	public ResidenceFeature(Residence property, AdditionalFeature feature) {
		super();
		this.property = property;
		this.feature = feature;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Residence getProperty() {
		return property;
	}

	public void setProperty(Residence property) {
		this.property = property;
	}

	public AdditionalFeature getFeature() {
		return feature;
	}

	public void setFeature(AdditionalFeature feature) {
		this.feature = feature;
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
		ResidenceFeature other = (ResidenceFeature) obj;
		return Objects.equals(id, other.id);
	}

}
