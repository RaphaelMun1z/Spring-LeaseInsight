package com.rm.leaseinsight.entities.enums;

public enum PropertyType {
	HOUSE(1),
	CONDO(2),
	FARM(3),
	WAREHOUSE(4),
	COMMERCIAL_APARTMENT(5),
	RETAIL_STORE(6),
	APARTMENT(7),
	LAND_PLOT(8);

	private int code;

	private PropertyType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static PropertyType valueOf(int code) {
		for (PropertyType value : PropertyType.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid PropertyType code.");
	}
}
