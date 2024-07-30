package com.rm.myadmin.entities.enums;

public enum TenantStatus {
	ACTIVE(1),
	INACTIVE(2),
	PENDING(3),
	FORMER(4),
	PROSPECTIVE(5);

	private int code;

	private TenantStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static TenantStatus valueOf(int code) {
		for (TenantStatus value : TenantStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid TenantStatus code.");
	}
}
