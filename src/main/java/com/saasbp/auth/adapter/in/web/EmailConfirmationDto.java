package com.saasbp.auth.adapter.in.web;

import java.util.UUID;

import javax.validation.constraints.NotNull;

public class EmailConfirmationDto {

	@NotNull
	private UUID code;

	public UUID getCode() {
		return code;
	}

	public void setCode(UUID code) {
		this.code = code;
	}
}
