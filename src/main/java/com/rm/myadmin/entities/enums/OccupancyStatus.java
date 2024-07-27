package com.rm.myadmin.entities.enums;

public enum OccupancyStatus {
	Occupied(1),
	Vacant(2),
	PendingMoveIn(3),
	PendingMoveOut(4),
	UnderMaintenance(5),
	Leased(6),
	Available(7),
	Reserved(8);

	private int code;

	private OccupancyStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static OccupancyStatus valueOf(int code) {
		for (OccupancyStatus value : OccupancyStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid PaymentStatus code.");
	}
}
