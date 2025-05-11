package com.rm.leaseinsight.dto.res;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.rm.leaseinsight.entities.RentalHistory;
import com.rm.leaseinsight.entities.enums.PaymentStatus;

public class RentalHistoryMinimalResponseDTO extends RepresentationModel<RentalHistoryMinimalResponseDTO>
		implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private LocalDate rentalStartDate;
	private LocalDate rentalEndDate;
	private Double rentalValue;
	private PaymentStatus paymentStatus;
	private String contractId;
	private String residenceId;

	public RentalHistoryMinimalResponseDTO() {
	}

	public RentalHistoryMinimalResponseDTO(RentalHistory rentalHistory) {
		super();
		this.id = rentalHistory.getId();
		this.rentalStartDate = rentalHistory.getRentalStartDate();
		this.rentalEndDate = rentalHistory.getRentalEndDate();
		this.rentalValue = rentalHistory.getRentalValue();
		this.paymentStatus = rentalHistory.getPaymentStatus();
		this.contractId = rentalHistory.getContract().getId();
		this.residenceId = rentalHistory.getContract().getResidence().getId();
	}

	public RentalHistoryMinimalResponseDTO(RentalHistoryResponseDTO rentalHistory) {
		super();
		this.id = rentalHistory.getId();
		this.rentalStartDate = rentalHistory.getRentalStartDate();
		this.rentalEndDate = rentalHistory.getRentalEndDate();
		this.rentalValue = rentalHistory.getRentalValue();
		this.paymentStatus = rentalHistory.getPaymentStatus();
		this.contractId = rentalHistory.getContract().getId();
		this.residenceId = rentalHistory.getContract().getResidence().getId();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getRentalStartDate() {
		return rentalStartDate;
	}

	public void setRentalStartDate(LocalDate rentalStartDate) {
		this.rentalStartDate = rentalStartDate;
	}

	public LocalDate getRentalEndDate() {
		return rentalEndDate;
	}

	public void setRentalEndDate(LocalDate rentalEndDate) {
		this.rentalEndDate = rentalEndDate;
	}

	public Double getRentalValue() {
		return rentalValue;
	}

	public void setRentalValue(Double rentalValue) {
		this.rentalValue = rentalValue;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getResidenceId() {
		return residenceId;
	}

	public void setResidenceId(String residenceId) {
		this.residenceId = residenceId;
	}

}
