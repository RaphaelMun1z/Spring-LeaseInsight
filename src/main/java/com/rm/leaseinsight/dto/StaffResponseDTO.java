package com.rm.leaseinsight.dto;

import com.rm.leaseinsight.entities.Staff;

public class StaffResponseDTO {
	private String id;
	private String name;
	private String phone;
	private String email;

	public StaffResponseDTO() {
	}

	public StaffResponseDTO(Staff staff) {
		super();
		this.id = staff.getId();
		this.name = staff.getName();
		this.phone = staff.getPhone();
		this.email = staff.getEmail();
	}

	public String getId() {
		return id;
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
