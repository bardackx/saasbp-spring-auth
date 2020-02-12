package com.saasbp.auth.adapter.in.web;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.saasbp.auth.application.port.in.EmailResetUseCase;

@RestController
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class EmailResetController {

	@Autowired
	private EmailResetUseCase useCase;

	@PostMapping("users/{uuid}/email-resets")
	public void requestEmailReset(@PathVariable("uuid") UUID uuid, @Valid @RequestBody EmailDto dto) {
		useCase.requestEmailReset(uuid, dto.getEmail());
	}
	
	@PostMapping("email-resets/{code}/confirmation")
	public void fulfillEmailReset(@PathVariable("code") UUID code) {
		useCase.fulfillEmailReset(code);
	}
}
