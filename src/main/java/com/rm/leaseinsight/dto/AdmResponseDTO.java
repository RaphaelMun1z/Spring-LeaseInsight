package com.rm.leaseinsight.dto;

import com.rm.leaseinsight.entities.Adm;

public class AdmResponseDTO {
	private String id;
	private String name;
	private String phone;
	private String email;

	public AdmResponseDTO() {
	}

	public AdmResponseDTO(Adm adm) {
		super();
		this.id = adm.getId();
		this.name = adm.getName();
		this.phone = adm.getPhone();
		this.email = adm.getEmail();
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
