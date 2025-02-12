package com.rm.leaseinsight.entities.validation;

import com.rm.leaseinsight.entities.validation.constraints.CEP;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CEPValidator implements ConstraintValidator<CEP, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		String cep = value == null ? "" : value;

		return cep.matches("^\\d{2}\\.\\d{3}[-]\\d{3}$");
	}

}
