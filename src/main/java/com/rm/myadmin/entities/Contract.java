package com.rm.myadmin.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_contract")
public class Contract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// private Long residence_id;
	// private Long tenant_id;
	private LocalDate contract_start_date;
	private LocalDate contract_end_date;
	private Double rental_value;
	private String contract_status;

	public Contract() {

	}

	public Contract(Long id, LocalDate contract_start_date, LocalDate contract_end_date, Double rental_value,
			String contract_status) {
		super();
		this.id = id;
		this.contract_start_date = contract_start_date;
		this.contract_end_date = contract_end_date;
		this.rental_value = rental_value;
		this.contract_status = contract_status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getContract_start_date() {
		return contract_start_date;
	}

	public void setContract_start_date(LocalDate contract_start_date) {
		this.contract_start_date = contract_start_date;
	}

	public LocalDate getContract_end_date() {
		return contract_end_date;
	}

	public void setContract_end_date(LocalDate contract_end_date) {
		this.contract_end_date = contract_end_date;
	}

	public Double getRental_value() {
		return rental_value;
	}

	public void setRental_value(Double rental_value) {
		this.rental_value = rental_value;
	}

	public String getContract_status() {
		return contract_status;
	}

	public void setContract_status(String contract_status) {
		this.contract_status = contract_status;
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
		Contract other = (Contract) obj;
		return Objects.equals(id, other.id);
	}

}
