package com.rm.myadmin.entities.enums;

public enum TemplatesEnum {
	WELCOME(1), INVOICE(2);

	private Integer code;

	private TemplatesEnum(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public static ContractStatus valueOf(Integer code) {
		for (ContractStatus value : ContractStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid ContractStatus code.");
	}
}
