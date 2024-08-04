package com.rm.myadmin.entities.validation;

import com.rm.myadmin.entities.validation.constraints.RG;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RGValidator implements ConstraintValidator<RG, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		String rg = value == null ? "" : value;

		return rg.matches("(\\d{1,2}\\.?)(\\d{3}\\.?)(\\d{3})(\\-?[0-9Xx]{1})");
	}

}
