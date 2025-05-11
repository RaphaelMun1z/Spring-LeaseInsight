package com.rm.leaseinsight.dto.req;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.Year;

import com.rm.leaseinsight.entities.enums.OccupancyStatus;
import com.rm.leaseinsight.entities.enums.PropertyType;

public class ResidenceRequestDTO {
	private PropertyType propertyType;
	private String description;
	private Integer number;
	private Integer aptNumber;
	private String complement;
	private int numberBedrooms;
	private int numberBathrooms;
	private int numberSuites;
	private float totalArea;
	private float builtArea;
	private int garageSpaces;
	private Year yearConstruction;
	private OccupancyStatus occupancyStatus;
	private BigDecimal marketValue;
	private BigDecimal rentalValue;
	private Instant dateLastRenovation;

	public ResidenceRequestDTO(PropertyType propertyType, String description, Integer number, Integer aptNumber,
			String complement, int numberBedrooms, int numberBathrooms, int numberSuites, float totalArea,
			float builtArea, int garageSpaces, Year yearConstruction, OccupancyStatus occupancyStatus,
			BigDecimal marketValue, BigDecimal rentalValue, Instant dateLastRenovation) {
		super();
		this.propertyType = propertyType;
		this.description = description;
		this.number = number;
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
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
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
		return occupancyStatus;
	}

	public void setOccupancyStatus(OccupancyStatus occupancyStatus) {
		this.occupancyStatus = occupancyStatus;
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
}
