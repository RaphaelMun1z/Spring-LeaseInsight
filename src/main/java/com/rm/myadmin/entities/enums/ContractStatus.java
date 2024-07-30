package com.rm.myadmin.entities.enums;

public enum ContractStatus {
	ACTIVE(1),
	TERMINATED(2),
	EXPIRED(3),
	PENDING_APPROVAL(4),
	APPROVED(5),
	REJECTED(6),
	UNDER_REVIEW(7),
	RENEWED(8),
	CANCELED(9),
	SUSPENDED(10),
	IN_NEGOTIATION(11),
	HOLD(12);

	private int code;

	private ContractStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static ContractStatus valueOf(int code) {
		for (ContractStatus value : ContractStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid ContractStatus code.");
	}
}
