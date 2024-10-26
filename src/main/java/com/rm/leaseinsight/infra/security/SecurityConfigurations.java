package com.rm.leaseinsight.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurations {
	@Autowired
	SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(
			                    "/swagger-ui.html",
			                    "/swagger-ui/*",
			                    "/v3/api-docs/**",
			                    "/swagger-resources/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
						.requestMatchers(HttpMethod.PUT, "/cache/**").hasAnyRole("ADM", "STAFF", "OWNER", "TENANT")
						.requestMatchers(HttpMethod.DELETE, "/cache/clear/**").hasAnyRole("ADM")
						.requestMatchers(HttpMethod.GET, "/additional-features/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/additional-features/**").hasAnyRole("ADM", "STAFF")
						.requestMatchers(HttpMethod.PUT, "/additional-features/**").hasAnyRole("ADM", "STAFF")
						.requestMatchers(HttpMethod.DELETE, "/additional-features/**").hasAnyRole("ADM", "STAFF")
						.requestMatchers(HttpMethod.PATCH, "/additional-features/**").hasAnyRole("ADM", "STAFF")
						.requestMatchers("/adm/**").hasAnyRole("ADM")
						.requestMatchers("/billing-addresses/**").hasAnyRole("ADM", "STAFF")
						.requestMatchers("/contracts/**").hasAnyRole("ADM", "STAFF")
						.requestMatchers("/owners/**").hasAnyRole("ADM", "STAFF")
						.requestMatchers("/rental-histories/**").hasAnyRole("ADM", "STAFF")
						.requestMatchers("/residence-addresses/**").hasAnyRole("ADM", "STAFF")
						.requestMatchers("/residence-feature/**").hasAnyRole("ADM", "STAFF")
						.requestMatchers("/residences/**").hasAnyRole("ADM", "STAFF")
						.requestMatchers("/reports/**").hasAnyRole("ADM", "STAFF")
						.requestMatchers("/staffs/**").hasAnyRole("ADM")
						.requestMatchers("/tenants/**").hasAnyRole("ADM", "STAFF")
						.requestMatchers("/file/**").hasAnyRole("ADM", "STAFF")
						.requestMatchers("/users/**").authenticated())
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
