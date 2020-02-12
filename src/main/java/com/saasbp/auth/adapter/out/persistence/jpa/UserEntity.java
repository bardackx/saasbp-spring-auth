package com.saasbp.auth.adapter.out.persistence.jpa;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "user")
@Entity
public class UserEntity {

	@Id
	private UUID uuid;
	private String email;
	private byte[] passwordHash;
	private byte[] passwordSalt;
	private UUID emailConfirmationCode;
	private boolean isEmailConfirmed;

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(byte[] passwordHash) {
		this.passwordHash = passwordHash;
	}

	public byte[] getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(byte[] passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	public UUID getEmailConfirmationCode() {
		return emailConfirmationCode;
	}

	public void setEmailConfirmationCode(UUID emailConfirmationCode) {
		this.emailConfirmationCode = emailConfirmationCode;
	}

	public boolean isEmailConfirmed() {
		return isEmailConfirmed;
	}

	public void setEmailConfirmed(boolean isEmailConfirmed) {
		this.isEmailConfirmed = isEmailConfirmed;
	}

}
