package com.saasbp.auth.adapter.in.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saasbp.auth.adapter.in.other.JwtService;
import com.saasbp.auth.application.port.in.AuthenticationUseCase;
import com.saasbp.auth.domain.User;

@RestController
public class AuthenticationController {

	@Autowired
	private JwtService jwtFactory;
	
	@Autowired
	private AuthenticationUseCase useCase;

	@PostMapping("/authentications")
	public AuthenticationResponseDto authenticate(@RequestBody @Valid AuthenticationDto dto) {
		User user = useCase.authenticateUserByEmail(dto.getEmail(), dto.getPassword());
		String jwt = jwtFactory.generateJwt(user.getUuid().toString());
		return new AuthenticationResponseDto(user.getUuid(), jwt);
	}
}
