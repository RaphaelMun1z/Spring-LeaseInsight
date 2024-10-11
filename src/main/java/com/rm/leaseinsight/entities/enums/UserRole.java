package com.rm.leaseinsight.entities.enums;

public enum UserRole {
	TENANT("tenant"), OWNER("owner"), STAFF("staff"), ADM("adm");

	private String role;

	UserRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}
