package com.rm.myadmin.entities;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rm.myadmin.entities.pk.ResidenceFeaturePK;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_residence_feature")
public class ResidenceFeature implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ResidenceFeaturePK id = new ResidenceFeaturePK();

	public ResidenceFeature() {

	}

	public ResidenceFeature(Residence property, AdditionalFeature feature) {
		super();
		id.setResidence(property);
		id.setAdditionalFeature(feature);
	}

	@JsonIgnore
	public Residence getProperty() {
		return id.getResidence();
	}

	public void setProperty(Residence property) {
		id.setResidence(property);
	}

	public AdditionalFeature getFeature() {
		return id.getAdditionalFeature();
	}

	public void setFeature(AdditionalFeature feature) {
		id.setAdditionalFeature(feature);
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
