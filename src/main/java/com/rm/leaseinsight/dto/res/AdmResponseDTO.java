package com.rm.leaseinsight.dto.res;

import java.io.Serializable;

import com.rm.leaseinsight.entities.Adm;

public class AdmResponseDTO extends UserResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public AdmResponseDTO() {
	}

	public AdmResponseDTO(Adm adm) {
		super(adm);
	}
}
