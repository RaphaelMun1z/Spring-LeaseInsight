package com.rm.leaseinsight.dto.res;

import java.io.Serializable;
import java.time.LocalDate;

import com.rm.leaseinsight.entities.BillingAddress;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.entities.enums.TenantStatus;

public class TenantResponseDTO extends UserResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private LocalDate dateOfBirth;
	private String cpf;
	private String rg;
	private LocalDate registrationDate;
	private TenantStatus tenantStatus;
	private BillingAddress tenantBillingAddress;

	public TenantResponseDTO() {
	}

	public TenantResponseDTO(Tenant tenant) {
		super(tenant);
		this.dateOfBirth = tenant.getDateOfBirth();
		this.cpf = tenant.getCpf();
		this.rg = tenant.getRg();
		this.registrationDate = tenant.getRegistrationDate();
		this.tenantStatus = tenant.getTenantStatus();
		this.tenantBillingAddress = tenant.getTenantBillingAddress();
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public TenantStatus getTenantStatus() {
		return tenantStatus;
	}

	public void setTenantStatus(TenantStatus tenantStatus) {
		this.tenantStatus = tenantStatus;
	}

	public BillingAddress getTenantBillingAddress() {
		return tenantBillingAddress;
	}

	public void setTenantBillingAddress(BillingAddress tenantBillingAddress) {
		this.tenantBillingAddress = tenantBillingAddress;
	}

}
