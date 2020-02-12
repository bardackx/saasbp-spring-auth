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
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.google.gson.Gson;
import com.saasbp.auth.adapter.in.web.AuthenticationController;
import com.saasbp.auth.adapter.in.web.AuthenticationDto;
import com.saasbp.auth.adapter.in.web.AuthenticationResponseDto;
import com.saasbp.auth.adapter.in.web.EmailDto;
import com.saasbp.auth.adapter.out.other.HashedPasswordProvider;
import com.saasbp.auth.adapter.out.persistence.jpa.UserEntity;
import com.saasbp.auth.adapter.out.persistence.jpa.UsersCrudRepository;
import com.saasbp.auth.domain.HashedPassword;

@DBRider
@SpringBootTest
@AutoConfigureMockMvc
public class EmailResetIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UsersCrudRepository usersCrudRepository;

	@Autowired
	private HashedPasswordProvider hashedPasswordProvider;

	@Autowired
	private AuthenticationController authCtrl;

	@Test
	@DataSet({ "email-reset/request-email-reset-set.yml" })
	@ExpectedDataSet("email-reset/request-email-reset-expected.yml")
	public void requestEmailReset() throws Exception {

		final UUID uuid = UUID.fromString("5a7f91d3-4bab-47cd-a62d-d612a20fe982");
		final String email = "my@email.com";
		final String newEmail = "new@email.com";
		final String password = "snapbanana";

		UserEntity record = usersCrudRepository.findById(uuid).get();
		HashedPassword hp = hashedPasswordProvider.createHashedPasswordWithRandomSalt(password);
		byte[] hash = hp.getHash();
		byte[] salt = hp.getSalt();
		record.setPasswordHash(hash);
		record.setPasswordSalt(salt);
		usersCrudRepository.save(record);

		AuthenticationDto dto2 = new AuthenticationDto();
		dto2.setEmail(email);
		dto2.setPassword(password);

		AuthenticationResponseDto authenticate = authCtrl.authenticate(dto2);

		String jwt = authenticate.getJwt();

		// =====================================================================================

		EmailDto dto = new EmailDto();
		dto.setEmail(newEmail);

		String json = new Gson().toJson(dto);

		mockMvc.perform(post("/users/" + uuid + "/email-resets")//
				.header("Authorization", "Bearer " + jwt)//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(json))//
				.andDo(print())//
				.andExpect(status().isOk());
	}

	@Test // Está fallando, hay que debuggear un poco
	@DataSet({ "email-reset/fulfill-email-reset-set.yml" })
	@ExpectedDataSet("email-reset/fulfill-email-reset-expected.yml")
	public void fulfillEmailReset() throws Exception {

		final UUID uuid = UUID.fromString("5a7f91d3-4bab-47cd-a62d-d612a20fe982");
		final UUID code = UUID.fromString("2cb6932d-aab0-4e43-b082-e8395a3c8ed6");
		final String email = "my@email.com";
		final String password = "snapbanana";

		UserEntity record = usersCrudRepository.findById(uuid).get();
		HashedPassword hp = hashedPasswordProvider.createHashedPasswordWithRandomSalt(password);
		byte[] hash = hp.getHash();
		byte[] salt = hp.getSalt();
		record.setPasswordHash(hash);
		record.setPasswordSalt(salt);
		usersCrudRepository.save(record);

		AuthenticationDto dto2 = new AuthenticationDto();
		dto2.setEmail(email);
		dto2.setPassword(password);

		AuthenticationResponseDto authenticate = authCtrl.authenticate(dto2);

		String jwt = authenticate.getJwt();

		// =====================================================================================

		mockMvc.perform(post("/email-resets/" + code + "/confirmation")//
				.header("Authorization", "Bearer " + jwt)//
				.contentType(MediaType.APPLICATION_JSON))//
				.andDo(print())//
				.andExpect(status().isOk());
	}

}
