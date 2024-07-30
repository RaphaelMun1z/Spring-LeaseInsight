package com.rm.myadmin.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rm.myadmin.entities.enums.TenantStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_tenant")
public class Tenant extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	private LocalDate dateOfBirth;
	private String cpf;
	private String rg;
	private LocalDate registrationDate;
	private Integer tenantStatus;

	@ManyToOne
	@JoinColumn(name = "tenant_billing_address_id")
	private BillingAddress tenantBillingAddress;

	@JsonIgnore
	@OneToMany(mappedBy = "tenant")
	List<Contract> contracts = new ArrayList<>();

	public Tenant() {
		super();
	}

	public Tenant(Long id, String name, String phone, String email, String password, LocalDate dateOfBirth, String cpf,
			String rg, LocalDate registrationDate, TenantStatus tenantStatus, BillingAddress tenantBillingAddress) {
		super(id, name, phone, email, password);
		this.dateOfBirth = dateOfBirth;
		this.cpf = cpf;
		this.rg = rg;
		this.registrationDate = registrationDate;
		setTenantStatus(tenantStatus);
		this.tenantBillingAddress = tenantBillingAddress;
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
		return TenantStatus.valueOf(tenantStatus);
	}

	public void setTenantStatus(TenantStatus tenantStatus) {
		this.tenantStatus = tenantStatus.getCode();
	}

	public BillingAddress getTenantBillingAddress() {
		return tenantBillingAddress;
	}

	public void setTenantBillingAddress(BillingAddress tenantBillingAddress) {
		this.tenantBillingAddress = tenantBillingAddress;
	}

	public List<Contract> getContracts() {
		return contracts;
	}

}
