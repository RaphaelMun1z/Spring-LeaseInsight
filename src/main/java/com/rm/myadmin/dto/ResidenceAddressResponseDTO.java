package com.rm.myadmin.dto;

import com.rm.myadmin.entities.ResidenceAddress;

public class ResidenceAddressResponseDTO {
	private String id;
	private int number;
	private String street;
	private String district;
	private String city;
	private String state;
	private String country;
	private String cep;
	private String complement;

	public ResidenceAddressResponseDTO() {
	}

	public ResidenceAddressResponseDTO(ResidenceAddress residenceAddress) {
		super();
		this.id = residenceAddress.getId();
		this.number = residenceAddress.getNumber();
		this.street = residenceAddress.getStreet();
		this.district = residenceAddress.getDistrict();
		this.city = residenceAddress.getCity();
		this.state = residenceAddress.getState();
		this.country = residenceAddress.getCountry();
		this.cep = residenceAddress.getCep();
		this.complement = residenceAddress.getComplement();
	}

	public String getId() {
		return id;
	}

	public int getNumber() {
		return number;
	}

	public String getStreet() {
		return street;
	}

	public String getDistrict() {
		return district;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	public String getCep() {
		return cep;
	}

	public String getComplement() {
		return complement;
	}

}
