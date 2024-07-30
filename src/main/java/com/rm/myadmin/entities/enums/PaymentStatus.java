package com.rm.myadmin.entities.enums;

public enum PaymentStatus {
	PENDING(1),
	PAID(2),
	OVERDUE(3),
	CANCELED(4),
	IN_PROCESS(5),
	PARTIALLY_PAID(6),
	DISPUTED(7),
	REFUNDED(8),
	IN_COLLECTION(9);

	private int code;

	private PaymentStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static PaymentStatus valueOf(int code) {
		for (PaymentStatus value : PaymentStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid PaymentStatus code.");
	}
}
