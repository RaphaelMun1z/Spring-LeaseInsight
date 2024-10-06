package com.rm.myadmin.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.Year;

import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.entities.enums.OccupancyStatus;
import com.rm.myadmin.entities.enums.PropertyType;

public class ResidenceResponseDTO {
	private String id;
	private Integer propertyType;
	private Integer number;
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

	private ResidenceAddressResponseDTO residenceAddress;
	private OwnerResponseDTO owner;

	public ResidenceResponseDTO() {
	}

	public ResidenceResponseDTO(Residence residence) {
		super();
		this.id = residence.getId();
		setPropertyType(residence.getPropertyType());
		this.description = residence.getDescription();
		this.number = residence.getNumber();
		this.aptNumber = residence.getAptNumber();
		this.complement = residence.getComplement();
		this.numberBedrooms = residence.getNumberBedrooms();
		this.numberBathrooms = residence.getNumberBathrooms();
		this.numberSuites = residence.getNumberSuites();
		this.totalArea = residence.getTotalArea();
		this.builtArea = residence.getBuiltArea();
		this.garageSpaces = residence.getGarageSpaces();
		this.yearConstruction = residence.getYearConstruction();
		setOccupancyStatus(residence.getOccupancyStatus());
		this.marketValue = residence.getMarketValue();
		this.rentalValue = residence.getRentalValue();
		this.dateLastRenovation = residence.getDateLastRenovation();
		this.residenceAddress = new ResidenceAddressResponseDTO(residence.getResidenceAddress());
		this.owner = new OwnerResponseDTO(residence.getOwner());
	}

	public String getId() {
		return id;
	}

	public Integer getPropertyType() {
		return propertyType;
	}

	public Integer getNumber() {
		return number;
	}

	public void setPropertyType(PropertyType propertyType) {
		if (propertyType != null) {
			this.propertyType = propertyType.getCode();
		}
	}

	public String getDescription() {
		return description;
	}

	public Integer getAptNumber() {
		return aptNumber;
	}

	public String getComplement() {
		return complement;
	}

	public int getNumberBedrooms() {
		return numberBedrooms;
	}

	public int getNumberBathrooms() {
		return numberBathrooms;
	}

	public int getNumberSuites() {
		return numberSuites;
	}

	public float getTotalArea() {
		return totalArea;
	}

	public float getBuiltArea() {
		return builtArea;
	}

	public int getGarageSpaces() {
		return garageSpaces;
	}

	public Year getYearConstruction() {
		return yearConstruction;
	}

	public Integer getOccupancyStatus() {
		return occupancyStatus;
	}

	public void setOccupancyStatus(OccupancyStatus occupancyStatus) {
		if (occupancyStatus != null) {
			this.occupancyStatus = occupancyStatus.getCode();
		}
	}

	public BigDecimal getMarketValue() {
		return marketValue;
	}

	public BigDecimal getRentalValue() {
		return rentalValue;
	}

	public Instant getDateLastRenovation() {
		return dateLastRenovation;
	}

	public ResidenceAddressResponseDTO getResidenceAddress() {
		return residenceAddress;
	}

	public OwnerResponseDTO getOwner() {
		return owner;
	}

}
