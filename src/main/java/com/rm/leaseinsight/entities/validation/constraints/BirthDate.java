package com.rm.leaseinsight.entities.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.rm.leaseinsight.entities.validation.BirthDateValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = BirthDateValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDate {
	String message() default "Invalid Date";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}