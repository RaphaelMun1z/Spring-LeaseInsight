package com.rm.leaseinsight.dto.res;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rm.leaseinsight.entities.Adm;
import com.rm.leaseinsight.entities.Owner;
import com.rm.leaseinsight.entities.Staff;
import com.rm.leaseinsight.entities.Tenant;

public class UserDetailsResponseDTO {
	private String id;
	private String name;
	private String phone;
	private String email;
	private String role;
	private List<String> authorities;

	public UserDetailsResponseDTO() {

	}

	public UserDetailsResponseDTO(String id, String name, String phone, String email, String role,
			List<String> authorities) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.role = role;
		this.authorities = authorities;
	}

	public UserDetailsResponseDTO(UserDetails user) {
		if (user instanceof Adm) {
			this.id = ((Adm) user).getId();
			this.name = ((Adm) user).getName();
			this.phone = ((Adm) user).getPhone();
			this.email = ((Adm) user).getEmail();
			this.role = ((Adm) user).getRole();
			this.authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());
		}

		if (user instanceof Staff) {
			this.id = ((Staff) user).getId();
			this.name = ((Staff) user).getName();
			this.phone = ((Staff) user).getPhone();
			this.email = ((Staff) user).getEmail();
			this.role = ((Staff) user).getRole();
			this.authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());
		}

		if (user instanceof Tenant) {
			this.id = ((Tenant) user).getId();
			this.name = ((Tenant) user).getName();
			this.phone = ((Tenant) user).getPhone();
			this.email = ((Tenant) user).getEmail();
			this.role = ((Tenant) user).getRole();
			this.authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());
		}

		if (user instanceof Owner) {
			this.id = ((Owner) user).getId();
			this.name = ((Owner) user).getName();
			this.phone = ((Owner) user).getPhone();
			this.email = ((Owner) user).getEmail();
			this.role = ((Owner) user).getRole();
			this.authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());
		}
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

	public String getId() {
		return id;
	}
}
