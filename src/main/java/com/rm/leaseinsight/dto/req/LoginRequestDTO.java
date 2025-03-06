package com.rm.leaseinsight.dto.req;

import org.springframework.hateoas.RepresentationModel;
import jakarta.validation.constraints.NotNull;

public class LoginRequestDTO extends RepresentationModel<LoginRequestDTO> {
	@NotNull(message = "Required field")
	private String email;

	@NotNull(message = "Required field")
	private String password;

	public LoginRequestDTO() {
	}

	public LoginRequestDTO(String email, String password) {

		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
