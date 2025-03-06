package com.rm.leaseinsight.dto.req;

import com.rm.leaseinsight.entities.Owner;

public class OwnerRequestDTO {
	private String name;
	private String phone;
	private String email;

	public OwnerRequestDTO() {
	}

	public OwnerRequestDTO(Owner owner) {
		super();
		this.name = owner.getName();
		this.phone = owner.getPhone();
		this.email = owner.getEmail();
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
