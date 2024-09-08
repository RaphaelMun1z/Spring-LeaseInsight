package com.rm.myadmin.dto;

import com.rm.myadmin.entities.AdditionalFeature;

public class AdditionalFeatureResponseDTO {
	private Long id;
	private String feature;

	public AdditionalFeatureResponseDTO() {
	}

	public AdditionalFeatureResponseDTO(AdditionalFeature af) {
		this.id = af.getId();
		this.feature = af.getFeature();
	}

	public Long getId() {
		return id;
	}

	public String getFeature() {
		return feature;
	}

}
