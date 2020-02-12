package com.saasbp.auth.adapter.out.other;

import org.springframework.stereotype.Component;

import com.saasbp.auth.application.port.out.SendEmailConfirmationEmail;
import com.saasbp.auth.application.port.out.SendEmailResetEmail;
import com.saasbp.auth.application.port.out.SendPasswordResetEmail;
import com.saasbp.auth.domain.EmailReset;
import com.saasbp.auth.domain.PasswordReset;
import com.saasbp.auth.domain.User;

@Component
public class EmailSender implements SendEmailConfirmationEmail, SendEmailResetEmail, SendPasswordResetEmail {

	@Override
	public void sendEmailConfirmationEmail(User user) {
		System.out.println("Email para " + user.getEmail() + " con codigo de confirmación "
				+ user.getEmailConfirmation().getCode());
	}

	@Override
	public void sendEmailResetEmail(User user, EmailReset e) {
		System.out.println("Email para resetear email " + user.getEmail() + " con codigo de reseteo " + e.getUuid());
	}

	@Override
	public void sendPasswordResetEmail(User user, PasswordReset e) {
		System.out.println("Email para resetear password " + user.getEmail() + " con codigo de reseteo " + e.getUuid());
	}

}
