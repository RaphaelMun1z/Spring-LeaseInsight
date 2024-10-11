package com.rm.leaseinsight.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "tb_additional_feature", uniqueConstraints = { @UniqueConstraint(columnNames = { "feature" }) })

public class AdditionalFeature implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@NotNull(message = "Required field")
	@Pattern(regexp = "^[A-Z]+(.)*")
	@Column(unique = true)
	private String feature;

	public AdditionalFeature() {

	}

	public AdditionalFeature(String id, String feature) {
		super();
		this.id = id;
		this.feature = feature;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
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
		AdditionalFeature other = (AdditionalFeature) obj;
		return Objects.equals(id, other.id);
	}

}
