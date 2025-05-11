package com.rm.leaseinsight.services.exceptions;

import org.springframework.dao.NonTransientDataAccessException;

public class DataViolationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DataViolationException() {
		super("Resource already exists.");
	}

	public DataViolationException(String msg) {
		super(msg);
	}

	public DataViolationException(String type, NonTransientDataAccessException e) {
		super(type + ": " + e);
	}
}
