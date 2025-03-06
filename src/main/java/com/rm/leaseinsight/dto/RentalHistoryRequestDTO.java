package com.rm.leaseinsight.dto;

import java.time.LocalDate;

import com.rm.leaseinsight.entities.RentalHistory;
import com.rm.leaseinsight.entities.enums.PaymentStatus;

public class RentalHistoryRequestDTO {
	private LocalDate rentalStartDate;
	private Integer paymentStatus;
	private String contractId;

	public RentalHistoryRequestDTO() {
	}

	public RentalHistoryRequestDTO(RentalHistory rentalHistory) {
		this.rentalStartDate = rentalHistory.getRentalStartDate();
		setPaymentStatus(rentalHistory.getPaymentStatus());
		this.contractId = rentalHistory.getContract().getId();
	}

	public LocalDate getRentalStartDate() {
		return rentalStartDate;
	}

	public PaymentStatus getPaymentStatus() {
		return PaymentStatus.valueOf(paymentStatus);
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		if (paymentStatus != null) {
			this.paymentStatus = paymentStatus.getCode();
		}
	}

	public String getContractId() {
		return contractId;
	}

	@Override
	public String toString() {
		return "RentalHistoryRequestDTO [rentalStartDate=" + rentalStartDate + ", paymentStatus=" + paymentStatus
				+ ", contractId=" + contractId + "]";
	}
}
