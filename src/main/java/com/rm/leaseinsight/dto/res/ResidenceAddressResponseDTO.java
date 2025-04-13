package com.rm.leaseinsight.dto.res;

import java.io.Serializable;

import com.rm.leaseinsight.entities.ResidenceAddress;

public class ResidenceAddressResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	private String id;
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
