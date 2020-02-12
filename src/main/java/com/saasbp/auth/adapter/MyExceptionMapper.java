
package com.saasbp.auth.adapter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.saasbp.auth.application.port.in.EmailAlreadyUsedException;
import com.saasbp.auth.application.port.in.InvalidCode;
import com.saasbp.auth.application.port.in.UserNotFound;
import com.saasbp.common.security.PrincipalIsNotAuthenticated;

@ControllerAdvice
public class MyExceptionMapper extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = { PrincipalIsNotAuthenticated.class })
	protected ResponseEntity<Object> handleConflict(PrincipalIsNotAuthenticated e, WebRequest request) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		String message = "PrincipalIsNotAuthenticated";
		return handleExceptionInternal(e, message, new HttpHeaders(), status, request);
	}


	@ExceptionHandler(value = { UserNotFound.class })
	protected ResponseEntity<Object> handleConflict(UserNotFound e, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		String message = "User with " + e.getField() + " '" + e.getValue() + "' was not found";
		return handleExceptionInternal(e, message, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(value = { EmailAlreadyUsedException.class })
	protected ResponseEntity<Object> handleConflict(EmailAlreadyUsedException e, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		String message = "The email '" + e.getEmail() + "' is already used by a user";
		return handleExceptionInternal(e, message, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(value = { InvalidCode.class })
	protected ResponseEntity<Object> handleConflict(InvalidCode e, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		String message = "The confirmation code doesn't correspond to a pending email confirmation";
		return handleExceptionInternal(e, message, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Object body = ex.getBindingResult().toString();
		return handleExceptionInternal(ex, body, headers, status, request);
	}
}