package com.rm.myadmin.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super("Resource not found.");
	}

	public ResourceNotFoundException(Object id) {
		super("Resource not found. Id " + id);
	}

	public ResourceNotFoundException(String msg) {
		super(msg);
	}

	public ResourceNotFoundException(String msg, Object id) {
		super("Resource not found. " + msg + ". Id: " + id);
	}
}
