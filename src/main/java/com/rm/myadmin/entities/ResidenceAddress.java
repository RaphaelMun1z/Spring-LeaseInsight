package com.rm.myadmin.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_residence_address")
public class ResidenceAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int number;
	private String street;
	private String district;
	private String city;
	private String state;
	private String country;
	private String cep;
	private String complement;

	@JsonIgnore
	@OneToMany(mappedBy = "residenceAddress")
	private List<Residence> residences = new ArrayList<>();

	public ResidenceAddress() {

	}

	public ResidenceAddress(Long id, int number, String street, String district, String city, String state,
			String country, String cep, String complement) {
		super();
		this.id = id;
		this.number = number;
		this.street = street;
		this.district = district;
		this.city = city;
		this.state = state;
		this.country = country;
		this.cep = cep;
		this.complement = complement;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public List<Residence> getResidences() {
		return residences;
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
		ResidenceAddress other = (ResidenceAddress) obj;
		return Objects.equals(id, other.id);
	}

}
