package com.rm.myadmin.dto;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import com.rm.myadmin.entities.Contract;

public class ContractDTO {
	private Long id;
	private LocalDate contractStartDate;
	private LocalDate contractEndDate;
	private Double defaultRentalValue;
	private int contractStatus;
	private int invoiceDueDate;

	public ContractDTO() {

	}

	public ContractDTO(Long id, LocalDate contractStartDate, LocalDate contractEndDate, Double defaultRentalValue,
			Integer contractStatus, int invoiceDueDate) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Integer getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(Integer contractStatus) {
		this.contractStatus = contractStatus;
	}

	public int getInvoiceDueDate() {
		return invoiceDueDate;
	}

	public void setInvoiceDueDate(int invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
	}

}
