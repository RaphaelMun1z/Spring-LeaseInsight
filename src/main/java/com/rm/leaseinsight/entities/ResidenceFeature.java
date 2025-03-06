package com.rm.leaseinsight.entities;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rm.leaseinsight.entities.pk.ResidenceFeaturePK;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_residence_feature")
public class ResidenceFeature implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@EmbeddedId
	private ResidenceFeaturePK id = new ResidenceFeaturePK();

	public ResidenceFeature() {

	}

	public ResidenceFeature(Residence property, AdditionalFeature feature) {
		super();
		this.id.setProperty(property);
		this.id.setAdditionalFeature(feature);
	}

	public ResidenceFeaturePK getId() {
		return id;
	}

	@JsonIgnore
	public Residence getProperty() {
		return id.getProperty();
	}

	public void setProperty(Residence property) {
		id.setProperty(property);
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
