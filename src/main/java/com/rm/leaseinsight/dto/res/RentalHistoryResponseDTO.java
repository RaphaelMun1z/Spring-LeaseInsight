package com.rm.leaseinsight.dto.res;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.rm.leaseinsight.entities.RentalHistory;
import com.rm.leaseinsight.entities.enums.PaymentStatus;

public class RentalHistoryResponseDTO extends RepresentationModel<RentalHistoryResponseDTO> {
	private String id;
	private LocalDate rentalStartDate;
	private LocalDate rentalEndDate;
	private Double rentalValue;
	private PaymentStatus paymentStatus;
	private ContractResponseDTO contract;

	public RentalHistoryResponseDTO() {
	}

	public RentalHistoryResponseDTO(RentalHistory rentalHistory) {
		this.id = rentalHistory.getId();
		this.rentalStartDate = rentalHistory.getRentalStartDate();
		this.rentalEndDate = rentalHistory.getRentalEndDate();
		this.rentalValue = rentalHistory.getRentalValue();
		this.paymentStatus = rentalHistory.getPaymentStatus();
		this.contract = new ContractResponseDTO(rentalHistory.getContract());
	}

	public String getId() {
		return id;
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

	public ContractResponseDTO getContract() {
		return contract;
	}

	public void setContract(ContractResponseDTO contract) {
		this.contract = contract;
	}
}
