package com.rm.leaseinsight.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.Year;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rm.leaseinsight.entities.enums.ContractStatus;
import com.rm.leaseinsight.entities.enums.OccupancyStatus;
import com.rm.leaseinsight.entities.enums.PropertyType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_residence", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "number", "residence_address_id" }) })
public class Residence implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@NotNull(message = "Invalid field value")
	private Integer propertyType;

	@NotNull(message = "Required field")
	private String description;

	@NotNull(message = "Required field")
	private Integer number;

	private Integer aptNumber;

	private String complement;

	@NotNull(message = "Required field")
	private int numberBedrooms;

	@NotNull(message = "Required field")
	private int numberBathrooms;

	@NotNull(message = "Required field")
	private int numberSuites;

	@Min(value = 1)
	@NotNull(message = "Invalid field value")
	private float totalArea;

	@NotNull(message = "Required field")
	private float builtArea;

	@NotNull(message = "Required field")
	private int garageSpaces;

	private Year yearConstruction;

	@NotNull(message = "Invalid field value")
	private Integer occupancyStatus;

	@Min(value = 1)
	@NotNull(message = "Invalid field value")
	private BigDecimal marketValue;

	@Min(value = 1)
	@NotNull(message = "Invalid field value")
	private BigDecimal rentalValue;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	@NotNull(message = "Required field")
	private Instant dateLastRenovation;

	@NotNull(message = "Required field")
	@ManyToOne
	@JoinColumn(name = "residence_address_id")
	private ResidenceAddress residenceAddress;

	@NotNull(message = "Required field")
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@JsonIgnore
	@OneToMany(mappedBy = "id.property", cascade = CascadeType.ALL)
	private Set<ResidenceFeature> features = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "residence")
	private Set<Contract> contracts = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "residence")
	private Set<Report> reports = new HashSet<>();

	@OneToMany(mappedBy = "residence", cascade = CascadeType.ALL)
	private Set<ResidenceImageFile> images = new HashSet<>();

	public Residence() {

	}

	public Residence(String id, Owner owner, PropertyType propertyType, String description, Integer number,
			Integer aptNumber, String complement, int numberBedrooms, int numberBathrooms, int numberSuites,
			float totalArea, float builtArea, int garageSpaces, Year yearConstruction, OccupancyStatus occupancyStatus,
			BigDecimal marketValue, BigDecimal rentalValue, Instant dateLastRenovation,
			ResidenceAddress residenceAddress) {
		super();
		this.id = id;
		this.owner = owner;
		setPropertyType(propertyType);
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
		setOccupancyStatus(occupancyStatus);
		this.marketValue = marketValue;
		this.rentalValue = rentalValue;
		this.dateLastRenovation = dateLastRenovation;
		this.residenceAddress = residenceAddress;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<Contract> getContracts() {
		return contracts;
	}

	public Contract getActiveContract() {
		for (Contract contract : contracts) {
			if (contract.getContractStatus() == ContractStatus.ACTIVE) {
				return contract;
			}
		}
		return null;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
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

	public Set<ResidenceFeature> getFeatures() {
		return features;
	}

	public ResidenceAddress getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(ResidenceAddress residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	public Set<Report> getReports() {
		return reports;
	}

	public void addReport(Report report) {
		reports.add(report);
	}

	public Set<ResidenceImageFile> getFiles() {
		return images;
	}

	public void addFile(ResidenceImageFile file) {
		images.add(file);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Residence other = (Residence) obj;
		return Objects.equals(id, other.id);
	}

}
