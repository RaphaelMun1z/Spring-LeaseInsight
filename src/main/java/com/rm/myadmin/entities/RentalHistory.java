package com.rm.myadmin.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rm.myadmin.entities.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_rental_history")
public class RentalHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate rentalStartDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate rentalEndDate;

	private Double rentalValue;

	private Integer paymentStatus;

	@ManyToOne
	@JoinColumn(name = "contract_id")
	private Contract contract;

	public RentalHistory() {
	}

	public RentalHistory(Long id, LocalDate rentalStartDate, PaymentStatus paymentStatus, Contract contract) {
		super();
		this.id = id;
		this.contract = contract;
		this.rentalStartDate = rentalStartDate;
		this.rentalEndDate = rentalEndDateGenerator();
		setPaymentStatus(paymentStatus);
		setRentalValue();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public PaymentStatus getPayment_status() {
		return PaymentStatus.valueOf(paymentStatus);
	}

	public long getReferenceMonth() {
		long month = rentalStartDate.getMonthValue();
		return month;
	}

	private LocalDate rentalEndDateGenerator() {
		LocalDate date = LocalDate.of(rentalStartDate.getYear(), (int) getReferenceMonth(),
				contract.getInvoiceDueDate());
		
		LocalDate dueDate = LocalDate.of(rentalStartDate.getYear(), rentalStartDate.getMonthValue(), contract.getInvoiceDueDate());
		if (dueDate.isAfter(rentalStartDate)) {
			return date;
		}

		return date.plusMonths(1);
	}

	private double calculateRentalDifferenceValue(LocalDate startDate, LocalDate endDate) {
		long daysDifference = ChronoUnit.DAYS.between(startDate, endDate);
		double dailyRentalPrice = (double) contract.getDefaultRentalValue() / (double) rentalStartDate.lengthOfMonth();
		BigDecimal bdValue = new BigDecimal(daysDifference * dailyRentalPrice);
		bdValue = bdValue.setScale(2, RoundingMode.CEILING);
		return bdValue.doubleValue();
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		if (paymentStatus != null) {
			this.paymentStatus = paymentStatus.getCode();
		}
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Double getRentalValue() {
		return rentalValue;
	}

	public void setRentalValue() {
		this.rentalValue = calculateRentalDifferenceValue(rentalStartDate, rentalEndDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RentalHistory other = (RentalHistory) obj;
		return Objects.equals(id, other.id);
	}

}
