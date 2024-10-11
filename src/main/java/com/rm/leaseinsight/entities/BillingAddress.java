package com.rm.leaseinsight.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_billing_address", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "number", "street", "district", "city", "state", "cep" }) })
public class BillingAddress extends Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Required field")
	private Integer number;

	@JsonIgnore
	@OneToMany(mappedBy = "tenantBillingAddress")
	private Set<Tenant> tenants = new HashSet<>();

	public BillingAddress() {
		super();
	}

	public BillingAddress(String id, Integer number, String street, String district, String city, String state,
			String country, String cep, String complement) {
		super(id, street, district, city, state, country, cep, complement);
		this.number = number;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Set<Tenant> getTenants() {
		return tenants;
	}
}
