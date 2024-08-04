package com.rm.myadmin.entities.validation;

import com.rm.myadmin.entities.validation.constraints.PhoneNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		String phoneNumber = value == null ? "" : value;
		
		
		return phoneNumber.matches("^\\([1-9]{2}\\) (?:[2-8]|9[0-9])[0-9]{3}\\-[0-9]{4}$");
	}

}
