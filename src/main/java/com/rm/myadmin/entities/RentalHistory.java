package com.rm.myadmin.entities;

import java.io.Serializable;
import java.time.LocalDate;
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

	private Integer paymentStatus;

	@ManyToOne
	@JoinColumn(name = "contract_id")
	private Contract contract;

	public RentalHistory() {
	}

	public RentalHistory(Long id, LocalDate rentalStartDate, LocalDate rentalEndDate, PaymentStatus paymentStatus,
			Contract contract) {
		super();
		this.id = id;
		this.rentalStartDate = rentalStartDate;
		this.rentalEndDate = rentalEndDate;
		setPaymentStatus(paymentStatus);
		this.contract = contract;
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
