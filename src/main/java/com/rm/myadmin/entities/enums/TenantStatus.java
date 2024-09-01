package com.rm.myadmin.entities.enums;

public enum TenantStatus {
	ACTIVE(1),
	INACTIVE(2),
	PENDING(3),
	FORMER(4),
	PROSPECTIVE(5);

	private Integer code;

	private TenantStatus(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public static TenantStatus valueOf(Integer code) {
		for (TenantStatus value : TenantStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid TenantStatus code.");
	}
}
