package com.rm.leaseinsight.dto.res;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;

import com.rm.leaseinsight.entities.Adm;
import com.rm.leaseinsight.entities.Owner;
import com.rm.leaseinsight.entities.Staff;
import com.rm.leaseinsight.entities.Tenant;
import com.rm.leaseinsight.entities.User;

public class UserResponseDTO extends RepresentationModel<UserResponseDTO> {
	private String id;
	private String name;
	private String phone;
	private String email;
	private String role;
	private List<String> authorities;

	public UserResponseDTO() {
	}

	public UserResponseDTO(User user) {
		super();
		this.id = user.getId();
		this.name = user.getName();
		this.phone = user.getPhone();
		this.email = user.getEmail();
		this.role = user.getRole();
		this.authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
	}

	public UserResponseDTO(Adm user) {
		super();
		this.id = user.getId();
		this.name = user.getName();
		this.phone = user.getPhone();
		this.email = user.getEmail();
		this.role = user.getRole();
		this.authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
	}

	public UserResponseDTO(Staff user) {
		super();
		this.id = user.getId();
		this.name = user.getName();
		this.phone = user.getPhone();
		this.email = user.getEmail();
		this.role = user.getRole();
		this.authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
	}

	public UserResponseDTO(Owner user) {
		super();
		this.id = user.getId();
		this.name = user.getName();
		this.phone = user.getPhone();
		this.email = user.getEmail();
		this.role = user.getRole();
		this.authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
	}

	public UserResponseDTO(Tenant user) {
		super();
		this.id = user.getId();
		this.name = user.getName();
		this.phone = user.getPhone();
		this.email = user.getEmail();
		this.role = user.getRole();
		this.authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

}
