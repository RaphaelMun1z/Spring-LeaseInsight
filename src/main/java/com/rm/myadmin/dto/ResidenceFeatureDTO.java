package com.rm.myadmin.dto;

import org.springframework.beans.BeanUtils;

import com.rm.myadmin.entities.AdditionalFeature;
import com.rm.myadmin.entities.ResidenceFeature;
import com.rm.myadmin.entities.pk.ResidenceFeaturePK;

public class ResidenceFeatureDTO {
	private ResidenceFeaturePK id = new ResidenceFeaturePK();

	public ResidenceFeatureDTO() {

	}

	public ResidenceFeatureDTO(ResidenceFeaturePK id) {
		super();
		this.id = id;
	}

	public ResidenceFeatureDTO(ResidenceFeature residenceFeature) {
		BeanUtils.copyProperties(residenceFeature, this);
	}

	public AdditionalFeature getFeature() {
		return id.getAdditionalFeature();
	}

	public void setFeature(AdditionalFeature feature) {
		this.id.setAdditionalFeature(feature);
	}

}
