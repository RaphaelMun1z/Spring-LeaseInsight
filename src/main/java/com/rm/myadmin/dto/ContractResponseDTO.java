package com.rm.myadmin.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.rm.myadmin.entities.Contract;
import com.rm.myadmin.entities.RentalHistory;
import com.rm.myadmin.entities.Residence;
import com.rm.myadmin.entities.enums.ContractStatus;

public class ContractResponseDTO {
	private Long id;
	private LocalDate contractStartDate;
	private LocalDate contractEndDate;
	private Double defaultRentalValue;
	private int contractStatus;
	private int invoiceDueDate;
	private Residence residence;
	private TenantResponseDTO tenant;
	private Set<RentalHistory> rentals = new HashSet<>();

	public ContractResponseDTO() {
	}

	public ContractResponseDTO(Contract contract) {
		super();
		this.id = contract.getId();
		this.contractStartDate = contract.getContractStartDate();
		this.contractEndDate = contract.getContractEndDate();
		this.defaultRentalValue = contract.getDefaultRentalValue();
		setContractStatus(contract.getContractStatus());
		this.invoiceDueDate = contract.getInvoiceDueDate();
		this.residence = contract.getResidence();
		this.tenant = new TenantResponseDTO(contract.getTenant());
		this.rentals = contract.getRentals();
	}

	public Long getId() {
		return id;
	}

	public LocalDate getContractStartDate() {
		return contractStartDate;
	}

	public LocalDate getContractEndDate() {
		return contractEndDate;
	}

	public Double getDefaultRentalValue() {
		return defaultRentalValue;
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

	public Residence getResidence() {
		return residence;
	}

	public TenantResponseDTO getTenant() {
		return tenant;
	}

	public Set<RentalHistory> getRentals() {
		return rentals;
	}

}
