package com.saasbp.auth.adapter.out.other;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Component;

import com.saasbp.auth.application.port.out.CreateHashedPassword;
import com.saasbp.auth.application.port.out.CreateHashedPasswordWithRandomSalt;
import com.saasbp.auth.domain.HashedPassword;

@Component
public class HashedPasswordProvider implements CreateHashedPasswordWithRandomSalt, CreateHashedPassword {

	@Override
	public HashedPassword createHashedPasswordWithRandomSalt(String password) {
		return createHashedPassword(password, generateRandomSalt());
	}

	@Override
	public HashedPassword createHashedPassword(String password, byte[] salt) {
		return new HashedPassword(generatePasswordHash(password, salt), salt);
	}

	private static byte[] generateRandomSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}

	private static byte[] generatePasswordHash(String password, byte[] passwordSalt) {
		try {
			KeySpec spec = new PBEKeySpec(password.toCharArray(), passwordSalt, 16000, 128);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			return factory.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

}
