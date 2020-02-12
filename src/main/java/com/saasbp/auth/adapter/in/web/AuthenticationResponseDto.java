package com.saasbp.auth.adapter.in.web;

import java.util.UUID;

public class AuthenticationResponseDto {

	private UUID userUuid;
	private String jwt;

	public AuthenticationResponseDto(UUID userUuid, String jwt) {
		super();
		this.userUuid = userUuid;
		this.jwt = jwt;
	}

	public UUID getUserUuid() {
		return userUuid;
	}

	public String getJwt() {
		return jwt;
	}

}
