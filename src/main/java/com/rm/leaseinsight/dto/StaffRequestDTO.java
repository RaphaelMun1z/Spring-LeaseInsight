package com.rm.leaseinsight.dto;

import com.rm.leaseinsight.entities.Staff;

public class StaffRequestDTO {
	private String name;
	private String phone;
	private String email;

	public StaffRequestDTO() {
	}

	public StaffRequestDTO(Staff staff) {
		super();
		this.name = staff.getName();
		this.phone = staff.getPhone();
		this.email = staff.getEmail();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
