package com.saasbp.auth.adapter.out.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.saasbp.auth.adapter.out.persistence.jpa.PasswordResetEntity;
import com.saasbp.auth.adapter.out.persistence.jpa.PasswordResetsCrudRepository;
import com.saasbp.auth.application.port.out.FindPasswordResetByCode;
import com.saasbp.auth.application.port.out.SavePasswordReset;
import com.saasbp.auth.domain.PasswordReset;

@Component
public class PasswordResetsRepository implements SavePasswordReset, FindPasswordResetByCode {

	@Autowired
	private PasswordResetsCrudRepository crud;

	@Override
	public Optional<PasswordReset> findPasswordResetByCode(UUID code) {
		return crud.findById(code).map(this::map);
	}

	@Override
	public void savePasswordReset(PasswordReset e) {
		crud.save(map(e));
	}

	private PasswordReset map(PasswordResetEntity e) {
		return new PasswordReset(e.getUuid(), e.getEmail(), e.getTimestamp(), e.isFulfilled());
	}

	private PasswordResetEntity map(PasswordReset o) {
		PasswordResetEntity e = new PasswordResetEntity();
		e.setEmail(o.getEmail());
		e.setFulfilled(o.isFulfilled());
		e.setTimestamp(o.getTimestamp());
		e.setUuid(o.getUuid());
		return e;
	}

}
