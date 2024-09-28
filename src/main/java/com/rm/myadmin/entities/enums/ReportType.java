package com.rm.myadmin.entities.enums;

public enum ReportType {
	MAINTENANCE_ISSUE(1), UTILITY_FAILURE(2), SECURITY_CONCERN(3), PROPERTY_DAMAGE(4), GENERAL_COMPLAINT(5);

	private int code;

	private ReportType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static ReportType valueOf(int code) {
		for (ReportType value : ReportType.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid ReportType code.");
	}
}
