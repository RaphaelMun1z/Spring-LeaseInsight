package com.rm.myadmin.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.Year;

import org.springframework.beans.BeanUtils;

import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.entities.ResidenceAddress;
import com.rm.myadmin.entities.enums.OccupancyStatus;
import com.rm.myadmin.entities.enums.PropertyType;

public class ResidenceDTO {
	private Long id;
	private Integer propertyType;
	private String description;
	private Integer aptNumber;
	private String complement;
	private int numberBedrooms;
	private int numberBathrooms;
	private int numberSuites;
	private float totalArea;
	private float builtArea;
	private int garageSpaces;
	private Year yearConstruction;
	private Integer occupancyStatus;
	private BigDecimal marketValue;
	private BigDecimal rentalValue;
	private Instant dateLastRenovation;
	private ResidenceAddress residenceAddress;

	public ResidenceDTO() {

	}

	public ResidenceDTO(Long id, Integer propertyType, String description, Integer aptNumber, String complement,
			int numberBedrooms, int numberBathrooms, int numberSuites, float totalArea, float builtArea,
			int garageSpaces, Year yearConstruction, Integer occupancyStatus, BigDecimal marketValue,
			BigDecimal rentalValue, Instant dateLastRenovation, ResidenceAddress residenceAddress) {
		super();
		this.id = id;
		this.propertyType = propertyType;
		this.description = description;
		this.aptNumber = aptNumber;
		this.complement = complement;
		this.numberBedrooms = numberBedrooms;
		this.numberBathrooms = numberBathrooms;
		this.numberSuites = numberSuites;
		this.totalArea = totalArea;
		this.builtArea = builtArea;
		this.garageSpaces = garageSpaces;
		this.yearConstruction = yearConstruction;
		this.occupancyStatus = occupancyStatus;
		this.marketValue = marketValue;
		this.rentalValue = rentalValue;
		this.dateLastRenovation = dateLastRenovation;
		this.residenceAddress = residenceAddress;
	}

	public ResidenceDTO(Residence residence) {
		BeanUtils.copyProperties(residence, this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAptNumber() {
		return aptNumber;
	}

	public void setAptNumber(Integer aptNumber) {
		this.aptNumber = aptNumber;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public int getNumberBedrooms() {
		return numberBedrooms;
	}

	public void setNumberBedrooms(int numberBedrooms) {
		this.numberBedrooms = numberBedrooms;
	}

	public int getNumberBathrooms() {
		return numberBathrooms;
	}

	public void setNumberBathrooms(int numberBathrooms) {
		this.numberBathrooms = numberBathrooms;
	}

	public int getNumberSuites() {
		return numberSuites;
	}

	public void setNumberSuites(int numberSuites) {
		this.numberSuites = numberSuites;
	}

	public float getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(float totalArea) {
		this.totalArea = totalArea;
	}

	public float getBuiltArea() {
		return builtArea;
	}

	public void setBuiltArea(float builtArea) {
		this.builtArea = builtArea;
	}

	public int getGarageSpaces() {
		return garageSpaces;
	}

	public void setGarageSpaces(int garageSpaces) {
		this.garageSpaces = garageSpaces;
	}

	public Year getYearConstruction() {
		return yearConstruction;
	}

	public void setYearConstruction(Year yearConstruction) {
		this.yearConstruction = yearConstruction;
	}

	public OccupancyStatus getOccupancyStatus() {
		return OccupancyStatus.valueOf(occupancyStatus);
	}

	public void setOccupancyStatus(OccupancyStatus occupancyStatus) {
		if (occupancyStatus != null) {
			this.occupancyStatus = occupancyStatus.getCode();
		}
	}

	public BigDecimal getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(BigDecimal marketValue) {
		this.marketValue = marketValue;
	}

	public BigDecimal getRentalValue() {
		return rentalValue;
	}

	public void setRentalValue(BigDecimal rentalValue) {
		this.rentalValue = rentalValue;
	}

	public Instant getDateLastRenovation() {
		return dateLastRenovation;
	}

	public void setDateLastRenovation(Instant dateLastRenovation) {
		this.dateLastRenovation = dateLastRenovation;
	}

	public ResidenceAddress getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(ResidenceAddress residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

}
