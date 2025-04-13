package com.rm.leaseinsight.dto.req;

import java.time.LocalDate;

import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.entities.enums.TenantStatus;

public class TenantPatchRequestDTO {
	private String name;
	private String phone;
	private String email;
	private LocalDate dateOfBirth;
	private TenantStatus tenantStatus;

	public TenantPatchRequestDTO() {
	}

	public TenantPatchRequestDTO(Tenant tenant) {
		this.name = tenant.getName();
		this.phone = tenant.getPhone();
		this.email = tenant.getEmail();
		this.dateOfBirth = tenant.getDateOfBirth();
		this.tenantStatus = tenant.getTenantStatus();
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

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public TenantStatus getTenantStatus() {
		return this.tenantStatus;
	}
}
