package com.rm.myadmin.dto;

import com.rm.myadmin.entities.Owner;

public class OwnerResponseDTO {
	private String id;
	private String name;
	private String phone;
	private String email;

	public OwnerResponseDTO() {
	}

	public OwnerResponseDTO(Owner owner) {
		super();
		this.id = owner.getId();
		this.name = owner.getName();
		this.phone = owner.getPhone();
		this.email = owner.getEmail();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}
}
