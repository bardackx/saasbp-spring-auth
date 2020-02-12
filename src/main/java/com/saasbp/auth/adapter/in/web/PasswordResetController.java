package com.saasbp.auth.adapter.in.web;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saasbp.auth.application.port.in.PasswordResetUseCase;

@RestController
public class PasswordResetController {

	@Autowired
	private PasswordResetUseCase useCase;

	@PostMapping("password-resets")
	public void requestPasswordReset(@Valid @RequestBody EmailDto dto) {
		useCase.requestPasswordReset(dto.getEmail());
	}

	@PutMapping(path = "password-resets/{code}/confirmation", consumes = MediaType.TEXT_PLAIN_VALUE)
	public void fulfillPasswordReset(@PathVariable("code") UUID code, @RequestBody String newPassword) {
		useCase.fulfillPasswordReset(code, newPassword);
	}

}
