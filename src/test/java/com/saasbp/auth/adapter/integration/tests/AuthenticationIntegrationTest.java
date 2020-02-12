package com.saasbp.auth.adapter.integration.tests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.google.gson.Gson;
import com.saasbp.auth.adapter.in.web.AuthenticationDto;
import com.saasbp.auth.adapter.out.other.HashedPasswordProvider;
import com.saasbp.auth.adapter.out.persistence.jpa.UserEntity;
import com.saasbp.auth.adapter.out.persistence.jpa.UsersCrudRepository;
import com.saasbp.auth.domain.HashedPassword;

@DBRider
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UsersCrudRepository usersCrudRepository;

	@Autowired
	private HashedPasswordProvider hashedPasswordFactory;

	@Test
	@DataSet("no-users.yml")
	public void authenticate() throws Exception {

		final String email = "my@email.com";
		final String password = "myPassword123";
		final HashedPassword hp = hashedPasswordFactory.createHashedPasswordWithRandomSalt(password);
		final byte[] hash = hp.getHash();
		final byte[] salt = hp.getSalt();

		UserEntity e = new UserEntity();
		e.setUuid(UUID.randomUUID());
		e.setEmail(email);
		e.setPasswordHash(hash);
		e.setPasswordSalt(salt);
		e.setEmailConfirmed(true);
		usersCrudRepository.save(e);

		AuthenticationDto dto = new AuthenticationDto();
		dto.setEmail(email);
		dto.setPassword(password);

		String json = new Gson().toJson(dto);

		mockMvc.perform(post("/authentications")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(json))//
				.andDo(print())//
				.andExpect(status().isOk());
	}

}
