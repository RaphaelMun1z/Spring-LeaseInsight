package com.rm.leaseinsight.infra.security;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rm.leaseinsight.dto.TokenDTO;
import com.rm.leaseinsight.entities.User;

import jakarta.annotation.PostConstruct;

@Service
public class TokenService {
	@Value("${api.security.token.secret:secret}")
	private String secret;

	Algorithm algorithm = null;

	@PostConstruct
	protected void init() {
		secret = Base64.getEncoder().encodeToString(secret.getBytes());
		algorithm = Algorithm.HMAC256(secret.getBytes());
	}

	public TokenDTO generateToken(User user) {
		try {
			String username = user.getUsername();
			Instant createdAt = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
			Instant expiration = genExpirationDate();
			String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

			String token = JWT.create().withClaim("role", user.getRole()).withIssuedAt(createdAt)
					.withExpiresAt(expiration).withIssuer(issuerUrl).withSubject(user.getId()).sign(algorithm).strip();
			return new TokenDTO(username, createdAt, expiration, token);
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Error while generating token", exception);
		}
	}

	public String validateToken(String token) {
		try {
			String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

			return JWT.require(algorithm).withIssuer(issuerUrl).build().verify(token).getSubject();
		} catch (JWTVerificationException exception) {
			return "";
		}
	}

	private Instant genExpirationDate() {
		return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(12).toInstant();
	}
}
