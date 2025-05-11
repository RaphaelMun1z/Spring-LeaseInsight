package com.rm.leaseinsight.dto.res;

import java.io.Serializable;

import com.rm.leaseinsight.entities.Staff;

public class StaffResponseDTO extends UserResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public StaffResponseDTO() {
	}

	public StaffResponseDTO(Staff staff) {
		super(staff);
	}
}
