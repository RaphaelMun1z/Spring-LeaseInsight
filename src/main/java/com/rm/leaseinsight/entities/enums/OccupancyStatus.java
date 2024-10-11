package com.rm.leaseinsight.entities.enums;

public enum OccupancyStatus {
	OCCUPIED(1),
	VACANT(2),
	PENDING_MOVE_IN(3),
	PENDING_MOVE_OUT(4),
	UNDER_MAINTENANCE(5),
	LEASED(6),
	AVAILABLE(7),
	RESERVED(8);

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
