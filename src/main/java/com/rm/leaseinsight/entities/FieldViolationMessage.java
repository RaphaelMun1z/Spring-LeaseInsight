package com.rm.leaseinsight.entities;

public class FieldViolationMessage {
	private String fieldName;
	private String message;

	public FieldViolationMessage(String fieldName, String message) {
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getMessage() {
		return message;
	}
}
