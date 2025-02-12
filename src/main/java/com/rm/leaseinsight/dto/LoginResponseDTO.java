package com.rm.leaseinsight.dto;

import org.springframework.hateoas.RepresentationModel;

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