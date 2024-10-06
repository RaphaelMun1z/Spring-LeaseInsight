package com.rm.myadmin.entities;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rm.myadmin.entities.enums.UserRole;
import com.rm.myadmin.entities.validation.constraints.PhoneNumber;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_user")
public abstract class User implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@NotNull(message = "Required field")
	@Pattern(regexp = "^[A-Z]+(.)*")
	private String name;

	@PhoneNumber(message = "Invalid field value")
	@Column(unique = true)
	private String phone;

	@Email(message = "Invalid field value")
	@Column(unique = true)
	private String email;

	@NotNull(message = "Required field")
	private String password;

	@Enumerated(EnumType.STRING)
	private UserRole role;

	protected User() {

	}

	protected User(String id, String name, String phone, String email, String password, UserRole role) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.password = password;
		setRole(role);
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private void setRole(UserRole role) {
		if (role == null) {
			throw new IllegalStateException("Role cannot be null");
		}

		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (role == UserRole.ADM) {
			return List.of(new SimpleGrantedAuthority("ROLE_ADM"), new SimpleGrantedAuthority("ROLE_STAFF"));
		} else if (role == UserRole.STAFF) {
			return List.of(new SimpleGrantedAuthority("ROLE_STAFF"), new SimpleGrantedAuthority("ROLE_OWNER"),
					new SimpleGrantedAuthority("ROLE_TENANT"));
		} else if (role == UserRole.OWNER) {
			return List.of(new SimpleGrantedAuthority("ROLE_OWNER"));
		} else if (role == UserRole.TENANT) {
			return List.of(new SimpleGrantedAuthority("ROLE_TENANT"));
		} else {
			throw new IllegalArgumentException("Unexpected value: " + this.role);
		}
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

}
