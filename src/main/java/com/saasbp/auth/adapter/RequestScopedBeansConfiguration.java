package com.saasbp.auth.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import com.saasbp.auth.adapter.out.other.EmailSender;
import com.saasbp.auth.adapter.out.persistence.EmailResetsRepository;
import com.saasbp.auth.adapter.out.persistence.UsersRepository;
import com.saasbp.auth.application.port.in.EmailResetUseCase;
import com.saasbp.auth.application.service.EmailResetService;
import com.saasbp.common.security.PrincipalService;

@Configuration
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class RequestScopedBeansConfiguration {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private EmailSender emailResource;

	@Autowired
	private EmailResetsRepository emailResetsRepository;

	@Autowired
	private PrincipalService principalService;

	@Bean
	@Scope(WebApplicationContext.SCOPE_REQUEST)
	public EmailResetUseCase getEmailResetUseCaseBean() {
		System.out.println("new email reset");
		return new EmailResetService(emailResource, principalService, emailResetsRepository, emailResetsRepository,
				usersRepository, usersRepository);
	}

}
