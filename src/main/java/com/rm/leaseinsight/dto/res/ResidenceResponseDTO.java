package com.rm.leaseinsight.dto.res;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.rm.leaseinsight.entities.Residence;
import com.rm.leaseinsight.entities.ResidenceFeature;
import com.rm.leaseinsight.entities.ResidenceImageFile;
import com.rm.leaseinsight.entities.enums.OccupancyStatus;
import com.rm.leaseinsight.entities.enums.PropertyType;

public class ResidenceResponseDTO extends RepresentationModel<ResidenceResponseDTO> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private BigDecimal marketValue;
	private BigDecimal rentalValue;
	private String description;
	private int numberBedrooms;
	private int numberBathrooms;
	private int numberSuites;
	private float totalArea;
	private float builtArea;
	private int garageSpaces;
	private Year yearConstruction;
	private PropertyType propertyType;
	private OccupancyStatus occupancyStatus;
	private Integer number;
	private Integer aptNumber;
	private String complement;
	private Instant dateLastRenovation;

	private ResidenceAddressResponseDTO residenceAddress;
	private OwnerResponseDTO owner;
	private Set<ResidenceImageFile> files;
	private Set<ResidenceFeature> features = new HashSet<>();

	public ResidenceResponseDTO() {
	}

	public ResidenceResponseDTO(Residence residence) {
		this.id = residence.getId();
		this.marketValue = residence.getMarketValue();
		this.rentalValue = residence.getRentalValue();
		this.description = residence.getDescription();
		this.numberBedrooms = residence.getNumberBedrooms();
		this.numberBathrooms = residence.getNumberBathrooms();
		this.numberSuites = residence.getNumberSuites();
		this.totalArea = residence.getTotalArea();
		this.builtArea = residence.getBuiltArea();
		this.garageSpaces = residence.getGarageSpaces();
		this.yearConstruction = residence.getYearConstruction();
		this.propertyType = residence.getPropertyType();
		this.occupancyStatus = residence.getOccupancyStatus();
		this.number = residence.getNumber();
		this.aptNumber = residence.getAptNumber();
		this.complement = residence.getComplement();
		this.dateLastRenovation = residence.getDateLastRenovation();
		this.residenceAddress = new ResidenceAddressResponseDTO(residence.getResidenceAddress());
		this.owner = new OwnerResponseDTO(residence.getOwner());
		this.files = residence.getFiles();
		this.features = residence.getFeatures();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public OccupancyStatus getOccupancyStatus() {
		return occupancyStatus;
	}

	public void setOccupancyStatus(OccupancyStatus occupancyStatus) {
		this.occupancyStatus = occupancyStatus;
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

	public Instant getDateLastRenovation() {
		return dateLastRenovation;
	}

	public void setDateLastRenovation(Instant dateLastRenovation) {
		this.dateLastRenovation = dateLastRenovation;
	}

	public ResidenceAddressResponseDTO getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(ResidenceAddressResponseDTO residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	public OwnerResponseDTO getOwner() {
		return owner;
	}

	public void setOwner(OwnerResponseDTO owner) {
		this.owner = owner;
	}

	public Set<ResidenceImageFile> getFiles() {
		return files;
	}

	public void setFiles(Set<ResidenceImageFile> files) {
		this.files = files;
	}

	public Set<ResidenceFeature> getFeatures() {
		return features;
	}

	public void setFeatures(Set<ResidenceFeature> features) {
		this.features = features;
	}

	@Override
	public String toString() {
		return "ResidenceResponseDTO [id=" + id + ", marketValue=" + marketValue + ", rentalValue=" + rentalValue
				+ ", description=" + description + ", numberBedrooms=" + numberBedrooms + ", numberBathrooms="
				+ numberBathrooms + ", numberSuites=" + numberSuites + ", totalArea=" + totalArea + ", builtArea="
				+ builtArea + ", garageSpaces=" + garageSpaces + ", yearConstruction=" + yearConstruction
				+ ", propertyType=" + propertyType + ", occupancyStatus=" + occupancyStatus + ", number=" + number
				+ ", aptNumber=" + aptNumber + ", complement=" + complement + ", dateLastRenovation="
				+ dateLastRenovation + ", residenceAddress=" + residenceAddress + ", owner=" + owner + ", files="
				+ files + ", features=" + features + "]";
	}

}
