package com.rm.myadmin.dto;

import java.time.LocalDate;

import com.rm.myadmin.entities.Contract;
import com.rm.myadmin.entities.enums.ContractStatus;

public class ContractResponseDTO {
	private Long id;
	private LocalDate contractStartDate;
	private LocalDate contractEndDate;
	private Double defaultRentalValue;
	private int contractStatus;
	private int invoiceDueDate;
	private ResidenceResponseDTO residence;
	private TenantResponseDTO tenant;

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
		this.residence = new ResidenceResponseDTO(contract.getResidence());
		this.tenant = new TenantResponseDTO(contract.getTenant());
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

	public ResidenceResponseDTO getResidence() {
		return residence;
	}

	public TenantResponseDTO getTenant() {
		return tenant;
	}

}
