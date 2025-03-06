package com.rm.leaseinsight.dto.res;

import com.rm.leaseinsight.entities.ResidenceFeature;

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
