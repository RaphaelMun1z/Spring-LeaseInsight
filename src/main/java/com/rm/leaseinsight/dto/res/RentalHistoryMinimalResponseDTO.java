package com.rm.leaseinsight.dto.res;

import java.time.LocalDate;

import com.rm.leaseinsight.entities.RentalHistory;
import com.rm.leaseinsight.entities.enums.PaymentStatus;

public class RentalHistoryMinimalResponseDTO {
	private String id;
	private LocalDate rentalStartDate;
	private LocalDate rentalEndDate;
	private Double rentalValue;
	private Integer paymentStatus;
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
		setPaymentStatus(rentalHistory.getPaymentStatus());
		this.contractId = rentalHistory.getContract().getId();
		this.residenceId = rentalHistory.getContract().getResidence().getId();
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

	public String getContractId() {
		return contractId;
	}

	public String getResidenceId() {
		return residenceId;
	}
}
