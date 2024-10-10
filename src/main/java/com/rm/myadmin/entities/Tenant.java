package com.rm.myadmin.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rm.myadmin.entities.enums.TenantStatus;
import com.rm.myadmin.entities.enums.UserRole;
import com.rm.myadmin.entities.validation.constraints.BirthDate;
import com.rm.myadmin.entities.validation.constraints.RG;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_tenant")
public class Tenant extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	@BirthDate(message = "Invalid date of birth")
	private LocalDate dateOfBirth;

	@CPF(message = "Invalid CPF value")
	private String cpf;

	@RG(message = "Invalid RG value")
	private String rg;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Required field")
	private LocalDate registrationDate;

	@NotNull(message = "Invalid field value")
	private Integer tenantStatus;

	@NotNull(message = "Required field")
	@ManyToOne
	@JoinColumn(name = "tenant_billing_address_id")
	private BillingAddress tenantBillingAddress;

	@JsonIgnore
	@OneToMany(mappedBy = "tenant")
	Set<Contract> contracts = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "tenant")
	private Set<Report> reports = new HashSet<>();

	public Tenant() {
		super();
	}

	public Tenant(String id, String name, String phone, String email, String password, LocalDate dateOfBirth,
			String cpf, String rg, LocalDate registrationDate, TenantStatus tenantStatus,
			BillingAddress tenantBillingAddress) {
		super(id, name, phone, email, password, UserRole.TENANT);
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

	public Set<Contract> getContracts() {
		return contracts;
	}

	public Set<Report> getReports() {
		return reports;
	}

	@Override
	public String toString() {
		return "Tenant [dateOfBirth=" + dateOfBirth + ", cpf=" + cpf + ", rg=" + rg + ", registrationDate="
				+ registrationDate + ", tenantStatus=" + tenantStatus + ", tenantBillingAddress=" + tenantBillingAddress
				+ ", contracts=" + contracts + ", reports=" + reports + "]";
	}

}
