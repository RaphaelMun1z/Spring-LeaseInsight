package com.rm.leaseinsight.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_residence_address", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "street", "district", "city", "state", "cep" }) })
public class ResidenceAddress extends Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@OneToMany(mappedBy = "residenceAddress")
	private Set<Residence> residences = new HashSet<>();

	public ResidenceAddress() {
		super();
	}

	public ResidenceAddress(String id, String street, String district, String city, String state, String country,
			String cep, String complement) {
		super(id, street, district, city, state, country, cep, complement);
	}

	public Set<Residence> getResidences() {
		return residences;
	}
}
