package com.rm.myadmin.entities.enums;

public enum TenantStatus {
	Active(1),
	Inactive(2),
	Pending(3),
	Former(4),
	Prospective(5);

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
