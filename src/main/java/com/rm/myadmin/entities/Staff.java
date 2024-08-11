package com.rm.myadmin.entities;

import java.io.Serializable;

import com.rm.myadmin.entities.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_staff")
public class Staff extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	public Staff() {
		super();
	}

	public Staff(String id, String name, String phone, String email, String password) {
		super(id, name, phone, email, password, UserRole.STAFF);
	}

}
