package com.rm.leaseinsight.dto;

import java.time.LocalDate;

import com.rm.leaseinsight.entities.RentalHistory;
import com.rm.leaseinsight.entities.enums.PaymentStatus;

public class RentalHistoryResponseDTO {
	private String id;
	private LocalDate rentalStartDate;
	private LocalDate rentalEndDate;
	private Double rentalValue;
	private Integer paymentStatus;
	private ContractResponseDTO contract;

	public RentalHistoryResponseDTO() {
	}

	public RentalHistoryResponseDTO(RentalHistory rentalHistory) {
		super();
		this.id = rentalHistory.getId();
		this.rentalStartDate = rentalHistory.getRentalStartDate();
		this.rentalEndDate = rentalHistory.getRentalEndDate();
		this.rentalValue = rentalHistory.getRentalValue();
		setPaymentStatus(rentalHistory.getPaymentStatus());
		this.contract = new ContractResponseDTO(rentalHistory.getContract());
	}

	public String getId() {
		return id;
	}

	public LocalDate getRentalStartDate() {
		return rentalStartDate;
	}

	public LocalDate getRentalEndDate() {
		return rentalEndDate;
	}

	public Double getRentalValue() {
		return rentalValue;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		if (paymentStatus != null) {
			this.paymentStatus = paymentStatus.getCode();
		}
	}

	public ContractResponseDTO getContract() {
		return contract;
	}

	@Override
	public String toString() {
		return "RentalHistoryResponseDTO [id=" + id + ", rentalStartDate=" + rentalStartDate + ", rentalEndDate="
				+ rentalEndDate + ", rentalValue=" + rentalValue + ", paymentStatus=" + paymentStatus + ", contract="
				+ contract + "]";
	}

}
