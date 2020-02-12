package com.saasbp.auth.adapter.in.web;

import javax.validation.constraints.Email;

public class EmailDto {

	@Email
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
