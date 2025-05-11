package com.rm.leaseinsight.dto.req;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.rm.leaseinsight.entities.Residence;
import com.rm.leaseinsight.entities.enums.PropertyType;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

public class ResidenceFilterDTO {
	private Double rentalValue;
	private Integer propertyType;
	private String description;
	private String city;

	public ResidenceFilterDTO(Double rentalValue, Integer propertyType, String description, String city) {
		this.rentalValue = rentalValue;
		this.propertyType = propertyType;
		this.description = description;
		this.city = city;
	}

	public Double getRentalValue() {
		return rentalValue;
	}

	public PropertyType getPropertyType() {
		return PropertyType.valueOf(propertyType);
	}

	public String getDescription() {
		return description;
	}

	public String getCity() {
		return city;
	}

	public Specification<Residence> toSpec() {
		return (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (StringUtils.hasText(description)) {
				Path<String> descriptionField = root.<String>get("description");
				Predicate descriptionPredicate = builder.like(descriptionField, "%" + description + "%");
				predicates.add(descriptionPredicate);
			}

			if (rentalValue != null) {
				Path<Double> rentalValueField = root.get("rentalValue");
				predicates.add(builder.lessThanOrEqualTo(rentalValueField, rentalValue));
			}

			if (StringUtils.hasText(city)) {
				Path<String> cityField = root.join("residenceAddress").get("city");
				predicates.add(builder.like(cityField, "%" + city + "%"));
			}

			if (propertyType != null) {
				Path<Integer> propertyTypeField = root.get("propertyType");
				predicates.add(builder.equal(propertyTypeField, propertyType));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
