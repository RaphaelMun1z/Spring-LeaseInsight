package com.rm.myadmin.dto;

import com.rm.myadmin.entities.BillingAddress;

public class BillingAddressResponseDTO {
	private String id;
	private Integer number;
	private String street;
	private String district;
	private String city;
	private String state;
	private String country;
	private String cep;
	private String complement;

	public BillingAddressResponseDTO() {
	}

	public BillingAddressResponseDTO(BillingAddress billingAddress) {
		super();
		this.id = billingAddress.getId();
		this.number = billingAddress.getNumber();
		this.street = billingAddress.getStreet();
		this.district = billingAddress.getDistrict();
		this.city = billingAddress.getCity();
		this.state = billingAddress.getState();
		this.country = billingAddress.getCountry();
		this.cep = billingAddress.getCep();
		this.complement = billingAddress.getComplement();
	}

	public String getId() {
		return id;
	}

	public Integer getNumber() {
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
