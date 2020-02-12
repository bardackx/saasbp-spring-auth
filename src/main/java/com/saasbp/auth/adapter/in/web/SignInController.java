package com.saasbp.auth.adapter.in.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saasbp.auth.application.port.in.SignInUseCase;

@RestController
public class SignInController {

	@Autowired
	private SignInUseCase signIn;

	@Transactional
	@PostMapping("/user-registrations")
	public void signIn(@Valid @RequestBody SignInDto dto) {
		signIn.signIn(dto.getEmail(), dto.getPassword());
	}

	@Transactional
	@PostMapping("/email-confirmations")
	public void confirmEmail(@Valid @RequestBody EmailConfirmationDto dto) {
		signIn.confirmEmail(dto.getCode());
	}

	@Transactional
	@PostMapping("/email-confirmation-resends")
	public void resendConfirmationEmail(@RequestBody @Valid EmailDto dto) {
		signIn.resendConfirmationEmail(dto.getEmail());
	}
}
