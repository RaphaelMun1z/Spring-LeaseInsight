package com.rm.leaseinsight.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rm.leaseinsight.entities.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_owner")
public class Owner extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@OneToMany(mappedBy = "owner")
	private Set<Residence> properties = new HashSet<>();

	public Owner() {
		super();
	}

	public Owner(String id, String name, String phone, String email, String password) {
		super(id, name, phone, email, password, UserRole.OWNER);
	}

	public Set<Residence> getProperties() {
		return properties;
	}

}
