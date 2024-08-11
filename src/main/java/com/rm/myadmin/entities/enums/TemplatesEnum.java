package com.rm.myadmin.entities.enums;

public enum TemplatesEnum {
	WELCOME(1), INVOICE(2);

	private int code;

	private TemplatesEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static ContractStatus valueOf(int code) {
		for (ContractStatus value : ContractStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid ContractStatus code.");
	}
}
