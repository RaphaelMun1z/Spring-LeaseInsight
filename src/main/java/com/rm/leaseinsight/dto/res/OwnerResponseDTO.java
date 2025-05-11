package com.rm.leaseinsight.dto.res;

import java.io.Serializable;

import com.rm.leaseinsight.entities.Owner;

public class OwnerResponseDTO extends UserResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public OwnerResponseDTO() {
	}

	public OwnerResponseDTO(Owner owner) {
		super(owner);
	}
}
