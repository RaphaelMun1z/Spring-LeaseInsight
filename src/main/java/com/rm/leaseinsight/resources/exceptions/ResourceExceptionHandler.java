package com.rm.leaseinsight.resources.exceptions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.rm.leaseinsight.entities.FieldViolationMessage;
import com.rm.leaseinsight.services.exceptions.DataViolationException;
import com.rm.leaseinsight.services.exceptions.DatabaseException;
import com.rm.leaseinsight.services.exceptions.FieldViolationException;
import com.rm.leaseinsight.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ResourceExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", "Resource not found");
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(MissingServletRequestPartException.class)
	public ResponseEntity<StandardError> handleMissingFile(MissingServletRequestPartException e,
			HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", "A imagem é obrigatória e não foi enviada.");
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", "Database error");
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DataViolationException.class)
	public ResponseEntity<StandardError> dataViolation(DataViolationException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", "Data violation error");
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(FieldViolationException.class)
	public ResponseEntity<StandardError> fieldViolation(FieldViolationException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		for (FieldViolationMessage fm : e.getErrors()) {
			errors.put(fm.getFieldName(), fm.getMessage());
		}
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<StandardError> badCredentials(BadCredentialsException e, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", "Bad credentials.");
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, "Bad credentials.",
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> conflict(Exception e, HttpServletRequest request) {
		System.out.println("Unhandled exception: " + e);
		Map<String, String> errors = new HashMap<>();
		errors.put("error", "Unable to perform operation.");
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		StandardError err = new StandardError(Instant.now(), status.value(), errors, "Unable to perform operation.",
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
