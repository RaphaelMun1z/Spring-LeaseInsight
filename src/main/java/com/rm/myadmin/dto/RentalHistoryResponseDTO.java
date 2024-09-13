package com.rm.myadmin.dto;

import java.time.LocalDate;

import com.rm.myadmin.entities.Contract;
import com.rm.myadmin.entities.RentalHistory;
import com.rm.myadmin.entities.enums.PaymentStatus;

public class RentalHistoryResponseDTO {
	private Long id;
	private LocalDate rentalStartDate;
	private LocalDate rentalEndDate;
	private Double rentalValue;
	private Integer paymentStatus;
	private Contract contract;

	public RentalHistoryResponseDTO() {
	}

	public RentalHistoryResponseDTO(RentalHistory rentalHistory) {
		super();
		this.id = rentalHistory.getId();
		this.rentalStartDate = rentalHistory.getRentalStartDate();
		this.rentalEndDate = rentalHistory.getRentalEndDate();
		this.rentalValue = rentalHistory.getRentalValue();
		setPaymentStatus(rentalHistory.getPaymentStatus());
		this.contract = rentalHistory.getContract();
	}

	public Long getId() {
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

	public Contract getContract() {
		return contract;
	}

}
