package com.rm.myadmin.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.Year;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rm.myadmin.entities.enums.OccupancyStatus;
import com.rm.myadmin.entities.enums.PropertyType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_residence")
public class Residence implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@OneToOne(mappedBy = "residence")
	private Contract contract;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

	private Integer propertyType;
	private String description;
	private Integer aptNumber;

	@Column(nullable = true)
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

	@JsonIgnore
	@OneToMany(mappedBy = "id.property", cascade = CascadeType.ALL)
	private Set<ResidenceFeature> features = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "residence_address_id")
	private ResidenceAddress residenceAddress;

	public Residence() {

	}

	public Residence(Long id, Owner owner, PropertyType propertyType, String description, Integer aptNumber,
			String complement, int numberBedrooms, int numberBathrooms, int numberSuites, float totalArea,
			float builtArea, int garageSpaces, Year yearConstruction, OccupancyStatus occupancyStatus,
			BigDecimal marketValue, BigDecimal rentalValue, Instant dateLastRenovation,
			ResidenceAddress residenceAddress) {
		super();
		this.id = id;
		this.owner = owner;
		setPropertyType(propertyType);
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
		setOccupancyStatus(occupancyStatus);
		this.marketValue = marketValue;
		this.rentalValue = rentalValue;
		this.dateLastRenovation = dateLastRenovation;
		this.residenceAddress = residenceAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
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
