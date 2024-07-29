package com.rm.myadmin.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rm.myadmin.entities.enums.TenantStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_tenant")
public class Tenant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@OneToOne
	@MapsId
	private User user;
	
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

	}

	public Tenant(Long id, User user, LocalDate dateOfBirth, String cpf, String rg, LocalDate registrationDate,
			TenantStatus tenantStatus, BillingAddress tenantBillingAddress) {
		super();
		this.id = id;
		this.user = user;
		this.dateOfBirth = dateOfBirth;
		this.cpf = cpf;
		this.rg = rg;
		this.registrationDate = registrationDate;
		setTenantStatus(tenantStatus);
		this.tenantBillingAddress = tenantBillingAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tenant other = (Tenant) obj;
		return Objects.equals(id, other.id);
	}

}
