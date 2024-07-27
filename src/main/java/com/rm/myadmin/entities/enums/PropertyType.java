package com.rm.myadmin.entities.enums;

public enum PropertyType {
	House(1),
	Condo(2),
	Farm(3),
	Warehouse(4),
	CommercialApartment(5),
	RetailStore(6),
	Apartment(7),
	LandPlot(8);

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
