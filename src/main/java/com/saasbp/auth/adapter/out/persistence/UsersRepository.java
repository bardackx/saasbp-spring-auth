package com.saasbp.auth.adapter.out.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.saasbp.auth.adapter.out.persistence.jpa.UserEntity;
import com.saasbp.auth.adapter.out.persistence.jpa.UsersCrudRepository;
import com.saasbp.auth.application.port.out.FindEmailConfirmationByCode;
import com.saasbp.auth.application.port.out.FindUserByEmail;
import com.saasbp.auth.application.port.out.FindUserByUuid;
import com.saasbp.auth.application.port.out.IsEmailBeingUsed;
import com.saasbp.auth.application.port.out.SaveEmailConfirmation;
import com.saasbp.auth.application.port.out.SaveUser;
import com.saasbp.auth.domain.EmailConfirmation;
import com.saasbp.auth.domain.HashedPassword;
import com.saasbp.auth.domain.User;

@Component
public class UsersRepository implements IsEmailBeingUsed, FindUserByEmail, SaveUser, SaveEmailConfirmation,
		FindEmailConfirmationByCode, FindUserByUuid {

	@Autowired
	private UsersCrudRepository crudRepository;

	@Override
	public boolean isEmailBeingUsed(String email) {
		return crudRepository.existsByEmail(email);
	}

	@Override
	public Optional<User> findUserByEmail(String email) {
		return crudRepository.findByEmail(email).map(this::map);
	}

	@Override
	public Optional<User> findUserByUuid(UUID uuid) {
		return crudRepository.findById(uuid).map(this::map);
	}

	@Override
	public void saveUser(User user) {
		crudRepository.save(map(user));
	}

	@Override
	public void saveEmailConfirmation(EmailConfirmation e) {
		crudRepository.updateEmailConfirmation(e.getCode(), e.isConfirmed());
	}

	@Override
	public Optional<EmailConfirmation> findEmailConfirmationByCode(UUID code) {
		return crudRepository.findByEmailConfirmationCode(code)
				.map(e -> new EmailConfirmation(e.getEmailConfirmationCode(), e.isEmailConfirmed()));
	}

	private User map(UserEntity e) {
		User o = new User();
		o.setUuid(e.getUuid());
		o.setEmail(e.getEmail());
		o.setPassword(new HashedPassword(e.getPasswordHash(), e.getPasswordSalt()));
		o.setEmailConfirmation(new EmailConfirmation(e.getEmailConfirmationCode(), e.isEmailConfirmed()));
		return o;
	}

	private UserEntity map(User o) {
		UserEntity e = new UserEntity();
		e.setEmail(o.getEmail());
		e.setEmailConfirmationCode(o.getEmailConfirmation().getCode());
		e.setEmailConfirmed(o.getEmailConfirmation().isConfirmed());
		e.setPasswordHash(o.getPassword().getHash());
		e.setPasswordSalt(o.getPassword().getSalt());
		e.setUuid(o.getUuid());
		return e;
	}

}
