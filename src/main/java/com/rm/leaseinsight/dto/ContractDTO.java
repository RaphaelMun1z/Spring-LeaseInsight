package com.rm.leaseinsight.dto;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import com.rm.leaseinsight.entities.Contract;
import com.rm.leaseinsight.entities.enums.ContractStatus;

public class ContractDTO {
	private String id;
	private LocalDate contractStartDate;
	private LocalDate contractEndDate;
	private Double defaultRentalValue;
	private ContractStatus contractStatus;
	private Integer invoiceDueDate;

	public ContractDTO() {

	}

	public ContractDTO(String id, LocalDate contractStartDate, LocalDate contractEndDate, Double defaultRentalValue,
			ContractStatus contractStatus, Integer invoiceDueDate) {
		this.id = id;
		this.contractStartDate = contractStartDate;
		this.contractEndDate = contractEndDate;
		this.defaultRentalValue = defaultRentalValue;
		this.contractStatus = contractStatus;
		this.invoiceDueDate = invoiceDueDate;
	}

	public ContractDTO(Contract contract) {
		BeanUtils.copyProperties(contract, this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public void setInvoiceDueDate(Integer invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
	}

}
