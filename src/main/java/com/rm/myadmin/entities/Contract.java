package com.rm.myadmin.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rm.myadmin.entities.enums.ContractStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_contract")
public class Contract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@NotNull(message = "Required field")
	private LocalDate contractStartDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@NotNull(message = "Required field")
	private LocalDate contractEndDate;

	@NotNull(message = "Required field")
	private Double defaultRentalValue;

	@NotNull(message = "Invalid field value")
	private Integer contractStatus;

	@Min(value = 1)
	@Max(value = 31)
	@NotNull(message = "Required field")
	private int invoiceDueDate;

	@NotNull(message = "Required field")
	@OneToOne
	@JoinColumn(name = "residence_id")
	private Residence residence;

	@NotNull(message = "Required field")
	@ManyToOne
	@JoinColumn(name = "tenant_id")
	private Tenant tenant;

	@JsonIgnore
	@OneToMany(mappedBy = "contract")
	private Set<RentalHistory> rentals = new HashSet<>();

	public Contract() {

	}

	public Contract(Long id, Residence residence, Tenant tenant, LocalDate contractStartDate, LocalDate contractEndDate,
			Double defaultRentalValue, ContractStatus contractStatus, int invoiceDueDate) {
		super();
		this.id = id;
		this.residence = residence;
		this.tenant = tenant;
		this.contractStartDate = contractStartDate;
		this.contractEndDate = contractEndDate;
		this.defaultRentalValue = defaultRentalValue;
		setContractStatus(contractStatus);
		setInvoiceDueDate(invoiceDueDate);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Residence getResidence() {
		return residence;
	}

	public void setResidence(Residence residence) {
		this.residence = residence;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public LocalDate getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(LocalDate contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public LocalDate getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(LocalDate contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public long getContractPeriodInMonths() {
		long difference = ChronoUnit.MONTHS.between(contractStartDate, contractEndDate);
		return difference;
	}

	public Double getDefaultRentalValue() {
		return defaultRentalValue;
	}

	public void setDefaultRentalValue(Double defaultRentalValue) {
		this.defaultRentalValue = defaultRentalValue;
	}

	public ContractStatus getContractStatus() {
		return ContractStatus.valueOf(contractStatus);
	}

	public void setContractStatus(ContractStatus contractStatus) {
		if (contractStatus != null) {
			this.contractStatus = contractStatus.getCode();
		}
	}

	public int getInvoiceDueDate() {
		return invoiceDueDate;
	}

	public void setInvoiceDueDate(int invoiceDueDate) {
		if (invoiceDueDate >= 1 && invoiceDueDate <= 31) {
			this.invoiceDueDate = invoiceDueDate;
		}
	}

	public Set<RentalHistory> getRentals() {
		return rentals;
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
		Contract other = (Contract) obj;
		return Objects.equals(id, other.id);
	}

}
