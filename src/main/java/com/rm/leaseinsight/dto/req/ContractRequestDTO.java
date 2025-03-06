package com.rm.leaseinsight.dto.req;

import java.time.LocalDate;

import com.rm.leaseinsight.entities.Contract;
import com.rm.leaseinsight.entities.enums.ContractStatus;

public class ContractRequestDTO {
	private LocalDate contractStartDate;
	private LocalDate contractEndDate;
	private Double defaultRentalValue;
	private ContractStatus contractStatus;
	private int invoiceDueDate;
	private String residenceId;
	private String tenantId;

	public ContractRequestDTO() {
	}

	public ContractRequestDTO(Contract contract) {
		this.contractStartDate = contract.getContractStartDate();
		this.contractEndDate = contract.getContractEndDate();
		this.defaultRentalValue = contract.getDefaultRentalValue();
		this.contractStatus = contract.getContractStatus();
		this.invoiceDueDate = contract.getInvoiceDueDate();
		this.residenceId = contract.getResidence().getId();
		this.tenantId = contract.getTenant().getId();
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

	public Double getDefaultRentalValue() {
		return defaultRentalValue;
	}

	public void setDefaultRentalValue(Double defaultRentalValue) {
		this.defaultRentalValue = defaultRentalValue;
	}

	public ContractStatus getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(ContractStatus contractStatus) {
		this.contractStatus = contractStatus;
	}

	public int getInvoiceDueDate() {
		return invoiceDueDate;
	}

	public void setInvoiceDueDate(int invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
	}

	public String getResidenceId() {
		return residenceId;
	}

	public void setResidenceId(String residenceId) {
		this.residenceId = residenceId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
