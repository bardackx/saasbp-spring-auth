package com.saasbp.auth.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.saasbp.auth.adapter.out.other.EmailSender;
import com.saasbp.auth.adapter.out.other.HashedPasswordProvider;
import com.saasbp.auth.adapter.out.persistence.PasswordResetsRepository;
import com.saasbp.auth.adapter.out.persistence.UsersRepository;
import com.saasbp.auth.application.port.in.AuthenticationUseCase;
import com.saasbp.auth.application.port.in.PasswordResetUseCase;
import com.saasbp.auth.application.port.in.SignInUseCase;
import com.saasbp.auth.application.service.AuthenticationService;
import com.saasbp.auth.application.service.PasswordResetService;
import com.saasbp.auth.application.service.SignInService;

@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private EmailSender emailGateway;

	@Autowired
	private HashedPasswordProvider hashedPasswordResource;

	@Autowired
	private PasswordResetsRepository passwordResetsRepository;

	@Bean
	public SignInUseCase getSignInUseCaseBean() {
		System.out.println("new sign in");
		return new SignInService(hashedPasswordResource, emailGateway, usersRepository, usersRepository,
				usersRepository, usersRepository, usersRepository);
	}

	@Bean
	public AuthenticationUseCase getAuthenticationUseCaseBean() {
		System.out.println("new auth");
		return new AuthenticationService(usersRepository, hashedPasswordResource);
	}

	@Bean
	public PasswordResetUseCase getPasswordResetUseCaseBean() {
		System.out.println("new password reset");
		return new PasswordResetService(hashedPasswordResource, emailGateway, passwordResetsRepository,
				passwordResetsRepository, usersRepository, usersRepository);
	}

}
