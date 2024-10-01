package com.rm.myadmin.dto;

import com.rm.myadmin.entities.ResidenceFeature;

public class ResidenceFeatureResponseDTO {
	private String id;
	private String feature;

	public ResidenceFeatureResponseDTO() {
	}

	public ResidenceFeatureResponseDTO(ResidenceFeature residenceFeature) {
		super();
		this.id = residenceFeature.getId().getAdditionalFeature().getId();
		this.feature = residenceFeature.getId().getAdditionalFeature().getFeature();
	}

	public String getId() {
		return id;
	}

	public String getFeature() {
		return feature;
	}

}
