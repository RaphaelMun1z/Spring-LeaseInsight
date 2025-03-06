package com.rm.leaseinsight.dto.res;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.rm.leaseinsight.entities.Residence;
import com.rm.leaseinsight.entities.ResidenceAddress;
import com.rm.leaseinsight.entities.ResidenceImageFile;
import com.rm.leaseinsight.entities.enums.PropertyType;

public class ResidenceMinimalResponseDTO {
	private String id;
	private Integer propertyType;
	private int numberBedrooms;
	private float totalArea;
	private int garageSpaces;
	private BigDecimal rentalValue;
	private ResidenceAddress residenceAddress;
	private Set<ResidenceImageFile> files;

	public ResidenceMinimalResponseDTO() {

	}

	public ResidenceMinimalResponseDTO(String id, Integer propertyType, int numberBedrooms, float totalArea,
			int garageSpaces, BigDecimal rentalValue, ResidenceAddress residenceAddress,
			Set<ResidenceImageFile> files) {
		super();
		this.id = id;
		this.propertyType = propertyType;
		this.numberBedrooms = numberBedrooms;
		this.totalArea = totalArea;
		this.garageSpaces = garageSpaces;
		this.rentalValue = rentalValue;
		this.residenceAddress = residenceAddress;
		this.files = files;
	}

	public ResidenceMinimalResponseDTO(Residence residence) {
		BeanUtils.copyProperties(residence, this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PropertyType getPropertyType() {
		return PropertyType.valueOf(propertyType);
	}

	public void setPropertyType(PropertyType propertyType) {
		if (propertyType != null) {
			this.propertyType = propertyType.getCode();
		}
	}

	public int getNumberBedrooms() {
		return numberBedrooms;
	}

	public void setNumberBedrooms(int numberBedrooms) {
		this.numberBedrooms = numberBedrooms;
	}

	public float getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(float totalArea) {
		this.totalArea = totalArea;
	}

	public int getGarageSpaces() {
		return garageSpaces;
	}

	public void setGarageSpaces(int garageSpaces) {
		this.garageSpaces = garageSpaces;
	}

	public BigDecimal getRentalValue() {
		return rentalValue;
	}

	public void setRentalValue(BigDecimal rentalValue) {
		this.rentalValue = rentalValue;
	}

	public ResidenceAddress getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(ResidenceAddress residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	public Set<ResidenceImageFile> getFiles() {
		return files;
	}

	public void setFiles(Set<ResidenceImageFile> files) {
		this.files = files;
	}
}
