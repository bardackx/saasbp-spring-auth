package com.saasbp.auth.adapter.integration.tests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.saasbp.auth.adapter.in.web.EmailDto;

@DBRider
@SpringBootTest
@AutoConfigureMockMvc
public class PasswordResetIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DataSet("password-reset/request-password-reset-set.yml")
	@ExpectedDataSet("password-reset/request-password-reset-expected.yml")
	public void requestPasswordReset() throws Exception {

		final String email = "my@email.com";

		EmailDto dto = new EmailDto();
		dto.setEmail(email);

		String json = new Gson().toJson(dto);

		mockMvc.perform(post("/password-resets")//
				.contentType(MediaType.APPLICATION_JSON)//
				.content(json))//
				.andDo(print())//
				.andExpect(status().isOk());
	}

	@Test
	@DataSet("password-reset/fulfill-password-reset-set.yml")
	@ExpectedDataSet("password-reset/fulfill-password-reset-expected.yml")
	public void fulfillPasswordReset() throws Exception {

		UUID code = UUID.fromString("9ad8c0d3-4d42-4894-9498-3e6d80f37ad1");
		String newPassword = "noimporta";

		mockMvc.perform( //
				put("/password-resets/" + code + "/confirmation")//
						.contentType(MediaType.TEXT_PLAIN_VALUE)//
						.content(newPassword))//
				.andDo(print())//
				.andExpect(status().isOk());
		;
	}
}
