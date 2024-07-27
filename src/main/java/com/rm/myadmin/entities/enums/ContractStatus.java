package com.rm.myadmin.entities.enums;

public enum ContractStatus {
	Active(1),
	Terminated(2),
	Expired(3),
	PendingApproval(4),
	Approved(5),
	Rejected(6),
	UnderReview(7),
	Renewed(8),
	Canceled(9),
	Suspended(10),
	InNegotiation(11),
	Hold(12);

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
