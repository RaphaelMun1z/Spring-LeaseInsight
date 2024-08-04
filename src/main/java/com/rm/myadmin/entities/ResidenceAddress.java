package com.rm.myadmin.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_residence_address")
public class ResidenceAddress extends Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@OneToMany(mappedBy = "residenceAddress")
	private Set<Residence> residences = new HashSet<>();

	public ResidenceAddress() {
		super();
	}

	public ResidenceAddress(Long id, int number, String street, String district, String city, String state,
			String country, String cep, String complement) {
		super(id, number, street, district, city, state, country, cep, complement);
	}

	public Set<Residence> getResidences() {
		return residences;
	}
}
