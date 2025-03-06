package com.rm.leaseinsight.dto.res;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.rm.leaseinsight.entities.Contract;
import com.rm.leaseinsight.entities.enums.ContractStatus;
import com.rm.leaseinsight.mapper.Mapper;

public class ContractResponseDTO extends RepresentationModel<ContractResponseDTO> {
	private String id;
	private LocalDate contractStartDate;
	private LocalDate contractEndDate;
	private Double defaultRentalValue;
	private ContractStatus contractStatus;
	private int invoiceDueDate;
	private ResidenceResponseDTO residence;
	private TenantResponseDTO tenant;

	public ContractResponseDTO() {
	}

	public ContractResponseDTO(Contract contract) {
		this.id = contract.getId();
		this.contractStartDate = contract.getContractStartDate();
		this.contractEndDate = contract.getContractEndDate();
		this.defaultRentalValue = contract.getDefaultRentalValue();
		this.contractStatus = contract.getContractStatus();
		this.invoiceDueDate = contract.getInvoiceDueDate();
		this.residence = Mapper.modelMapper(contract.getResidence(), ResidenceResponseDTO.class);
		this.tenant = Mapper.modelMapper(contract.getTenant(), TenantResponseDTO.class);
	}

	public String getId() {
		return id;
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

	public ResidenceResponseDTO getResidence() {
		return residence;
	}

	public void setResidence(ResidenceResponseDTO residence) {
		this.residence = residence;
	}

	public TenantResponseDTO getTenant() {
		return tenant;
	}

	public void setTenant(TenantResponseDTO tenant) {
		this.tenant = tenant;
	}
}
