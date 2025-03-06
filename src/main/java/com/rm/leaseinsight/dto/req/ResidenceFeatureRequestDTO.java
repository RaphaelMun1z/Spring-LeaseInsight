package com.rm.leaseinsight.dto.req;

import com.rm.leaseinsight.entities.ResidenceFeature;

public class ResidenceFeatureRequestDTO {
	private String propertyId;
	private String featureId;

	public ResidenceFeatureRequestDTO() {}

	public ResidenceFeatureRequestDTO(ResidenceFeature residenceFeature) {
		this.propertyId = residenceFeature.getProperty().getId();
		this.featureId = residenceFeature.getId().getAdditionalFeature().getId();
	}

	public String getPropertyId() {
		return propertyId;
	}

	public String getFeatureId() {
		return featureId;
	}
}
