package com.saasbp.auth.adapter.out.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.saasbp.auth.adapter.out.persistence.jpa.EmailResetEntity;
import com.saasbp.auth.adapter.out.persistence.jpa.EmailResetsCrudRepository;
import com.saasbp.auth.application.port.out.FindEmailResetByCode;
import com.saasbp.auth.application.port.out.SaveEmailReset;
import com.saasbp.auth.domain.EmailReset;

@Component
public class EmailResetsRepository implements SaveEmailReset, FindEmailResetByCode {

	@Autowired
	private EmailResetsCrudRepository crud;

	@Override
	public Optional<EmailReset> findEmailResetByCode(UUID code) {
		return crud.findById(code).map(this::map);
	}

	@Override
	public void saveEmailReset(EmailReset e) {
		crud.save(map(e));
	}

	private EmailReset map(EmailResetEntity o) {
		return new EmailReset(o.getUuid(), o.getUser(), o.getNewEmail(), o.getTimestamp(), o.isFulfilled());
	}

	private EmailResetEntity map(EmailReset o) {
		EmailResetEntity e = new EmailResetEntity();
		e.setFulfilled(o.isFulfilled());
		e.setNewEmail(o.getNewEmail());
		e.setTimestamp(o.getTimestamp());
		e.setUser(o.getUser());
		e.setUuid(o.getUuid());
		return e;
	}

}
