package com.rm.myadmin.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_billing_address")
public class BillingAddress extends Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@OneToMany(mappedBy = "tenantBillingAddress")
	private List<Tenant> tenants = new ArrayList<>();

	public BillingAddress() {
		super();
	}

	public BillingAddress(Long id, int number, String street, String district, String city, String state,
			String country, String cep, String complement) {
		super(id, number, street, district, city, state, country, cep, complement);
	}

	public List<Tenant> getTenants() {
		return tenants;
	}
}
