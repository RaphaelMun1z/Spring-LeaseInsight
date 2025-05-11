package com.rm.leaseinsight.services.exceptions;

import java.util.List;

import com.rm.leaseinsight.entities.FieldViolationMessage;

public class FieldViolationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final List<FieldViolationMessage> errors;

	public FieldViolationException(List<FieldViolationMessage> errors) {
		super("Validation error");
		this.errors = errors;
	}

	public List<FieldViolationMessage> getErrors() {
		return errors;
	}
}
