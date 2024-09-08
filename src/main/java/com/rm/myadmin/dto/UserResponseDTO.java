package com.rm.myadmin.dto;

import com.rm.myadmin.entities.User;

public class UserResponseDTO {
	private String id;
	private String name;
	private String phone;
	private String email;

	public UserResponseDTO() {
	}

	public UserResponseDTO(User user) {
		super();
		this.id = user.getId();
		this.name = user.getName();
		this.phone = user.getPhone();
		this.email = user.getEmail();
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
