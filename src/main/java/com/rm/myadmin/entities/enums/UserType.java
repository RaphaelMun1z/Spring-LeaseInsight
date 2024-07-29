package com.rm.myadmin.entities.enums;

public enum UserType {
	ADM(1),
	CLIENT(2);

	private int code;

	private UserType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static UserType valueOf(int code) {
		for (UserType value : UserType.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid UserType code.");
	}
}
