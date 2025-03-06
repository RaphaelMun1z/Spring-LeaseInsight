package com.rm.leaseinsight.dto.res;

import org.springframework.hateoas.RepresentationModel;

import com.rm.leaseinsight.dto.TokenDTO;

public class LoginResponseDTO extends RepresentationModel<LoginResponseDTO> {
	private TokenDTO token;

	public LoginResponseDTO() {
	}

	public LoginResponseDTO(TokenDTO token) {
		this.token = token;
	}

	public TokenDTO getToken() {
		return token;
	}

	public void setToken(TokenDTO token) {
		this.token = token;
	}
}