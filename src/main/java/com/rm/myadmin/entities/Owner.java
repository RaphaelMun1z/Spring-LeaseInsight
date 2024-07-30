package com.rm.myadmin.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_owner")
public class Owner extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@OneToMany(mappedBy = "owner")
	private List<Residence> properties = new ArrayList<>();

	public Owner() {
		super();
	}

	public Owner(Long id, String name, String phone, String email, String password) {
		super(id, name, phone, email, password);
	}

	public List<Residence> getProperties() {
		return properties;
	}

}
