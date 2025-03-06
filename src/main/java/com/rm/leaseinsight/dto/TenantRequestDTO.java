package com.rm.leaseinsight.dto;

import java.time.LocalDate;

import com.rm.leaseinsight.entities.BillingAddress;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.entities.enums.TenantStatus;

public class TenantRequestDTO {
	private String name;
	private String phone;
	private String email;
	private LocalDate dateOfBirth;
	private String cpf;
	private String rg;
	private Integer tenantStatus;
	private BillingAddress tenantBillingAddress;

	public TenantRequestDTO() {
	}

	public TenantRequestDTO(Tenant tenant) {
		super();
		this.name = tenant.getName();
		this.phone = tenant.getPhone();
		this.email = tenant.getEmail();
		this.dateOfBirth = tenant.getDateOfBirth();
		this.cpf = tenant.getCpf();
		this.rg = tenant.getRg();
		setTenantStatus(tenant.getTenantStatus());
		this.tenantBillingAddress = tenant.getTenantBillingAddress();
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

	public String getCpf() {
		return cpf;
	}

	public String getRg() {
		return rg;
	}

	public TenantStatus getTenantStatus() {
		return TenantStatus.valueOf(tenantStatus);
	}

	public void setTenantStatus(TenantStatus tenantStatus) {
		this.tenantStatus = tenantStatus.getCode();
	}

	public BillingAddress getTenantBillingAddress() {
		return tenantBillingAddress;
	}

}
