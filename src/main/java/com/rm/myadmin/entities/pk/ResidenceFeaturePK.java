package com.rm.myadmin.entities.pk;

import java.io.Serializable;
import java.util.Objects;

import com.rm.myadmin.entities.AdditionalFeature;
import com.rm.myadmin.entities.Residence;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class ResidenceFeaturePK implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "residence_id")
	private Residence residence;

	@ManyToOne
	@JoinColumn(name = "additional_feature_id")
	private AdditionalFeature additionalFeature;

	public Residence getResidence() {
		return residence;
	}

	public void setResidence(Residence residence) {
		this.residence = residence;
	}

	public AdditionalFeature getAdditionalFeature() {
		return additionalFeature;
	}

	public void setAdditionalFeature(AdditionalFeature additionalFeature) {
		this.additionalFeature = additionalFeature;
	}

	@Override
	public int hashCode() {
		return Objects.hash(additionalFeature, residence);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResidenceFeaturePK other = (ResidenceFeaturePK) obj;
		return Objects.equals(additionalFeature, other.additionalFeature) && Objects.equals(residence, other.residence);
	}

}
