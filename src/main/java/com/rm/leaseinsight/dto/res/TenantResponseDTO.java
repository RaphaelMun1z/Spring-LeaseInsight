package com.rm.leaseinsight.dto.res;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.rm.leaseinsight.entities.BillingAddress;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.entities.enums.TenantStatus;

public class TenantResponseDTO extends RepresentationModel<TenantResponseDTO> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String phone;
	private String email;
	private LocalDate dateOfBirth;
	private String cpf;
	private String rg;
	private LocalDate registrationDate;
	private Integer tenantStatus;
	private BillingAddress tenantBillingAddress;

	public TenantResponseDTO() {
	}

	public TenantResponseDTO(Tenant tenant) {
		super();
		this.id = tenant.getId();
		this.name = tenant.getName();
		this.phone = tenant.getPhone();
		this.email = tenant.getEmail();
		this.dateOfBirth = tenant.getDateOfBirth();
		this.cpf = tenant.getCpf();
		this.rg = tenant.getRg();
		this.registrationDate = tenant.getRegistrationDate();
		setTenantStatus(tenant.getTenantStatus());
		this.tenantBillingAddress = tenant.getTenantBillingAddress();
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

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public String getCpf() {
		return cpf;
	}

	public String getRg() {
		return rg;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
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
