package com.rm.myadmin.entities.enums;

public enum PaymentStatus {
	Pending(1), Paid(2), Overdue(3), Canceled(4), InProcess(5), PartiallyPaid(6), Disputed(7), Refunded(8),
	InCollection(9);

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
