package com.saasbp.auth.adapter.integration.tests;

import static org.junit.Assert.assertEquals;
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
import com.saasbp.auth.adapter.in.web.EmailConfirmationDto;
import com.saasbp.auth.adapter.in.web.EmailDto;
import com.saasbp.auth.adapter.in.web.SignInDto;
import com.saasbp.auth.adapter.out.persistence.jpa.UsersCrudRepository;

/**
 * Integration Tests
 * <ul>
 * <li>Test integration with slow parts (http, database, etc.)
 * <li>Dev documentation: Does this work as expected?
 * <li>Test one layer in isolation (e.g. only rest endpoint, or only data
 * provider). Slow
 * <li>Use whatever library makes it easy (e.g. Spring MockMVC; in-memory db)
 * </ul>
 * 
 * @author barda
 *
 */
@DBRider
@SpringBootTest
@AutoConfigureMockMvc
public class SignInIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UsersCrudRepository usersCrudRepository;

	@Test
	@DataSet("no-users.yml")
	@ExpectedDataSet("sign-in/sign-in-expected.yml")
	public void signIn() throws Exception {

		SignInDto signInDto = new SignInDto();
		signInDto.setEmail("my@email.com");
		signInDto.setPassword("myPassword123");

		mockMvc.perform( //
				post("/user-registrations") //
						.contentType(MediaType.APPLICATION_JSON)//
						.content(new Gson().toJson(signInDto)))//
				.andDo(print())//
				.andExpect(status().isOk());

		assertEquals(1, usersCrudRepository.count());
	}

	@Test
	@DataSet("sign-in/confirm-email-set.yml")
	public void resendEmail() throws Exception {

		EmailDto dto = new EmailDto();
		dto.setEmail("my@email.com");

		String json = new Gson().toJson(dto);

		mockMvc.perform(post("/email-confirmation-resends").contentType(MediaType.APPLICATION_JSON).content(json))//
				.andDo(print())//
				.andExpect(status().isOk());
	}

	@Test
	@DataSet("sign-in/confirm-email-set.yml")
	@ExpectedDataSet("sign-in/confirm-email-expected.yml")
	public void confirmEmail() throws Exception {

		EmailConfirmationDto emailConfirmationDto = new EmailConfirmationDto();
		emailConfirmationDto.setCode(UUID.fromString("5a7f91d3-4bab-47cd-a62d-d612a20fe982"));

		mockMvc.perform( //
				post("/email-confirmations") //
						.contentType(MediaType.APPLICATION_JSON)//
						.content(new Gson().toJson(emailConfirmationDto))) //
				.andDo(print()) //
				.andExpect(status().isOk());
	}

	@Test
	public void badRequestAtSignIn() throws Exception {

		mockMvc.perform(post("/user-registrations").contentType(MediaType.APPLICATION_JSON))//
				.andDo(print())//
				.andExpect(status().isBadRequest());

		SignInDto dto = new SignInDto();

		String json = new Gson().toJson(dto);

		mockMvc.perform(post("/user-registrations").contentType(MediaType.APPLICATION_JSON).content(json))//
				.andDo(print())//
				.andExpect(status().isBadRequest());
	}

	@Test
	public void badRequestAtResendEmail() throws Exception {

		mockMvc.perform(post("/email-confirmation-resends").contentType(MediaType.APPLICATION_JSON))//
				.andDo(print())//
				.andExpect(status().isBadRequest());

		EmailDto dto = new EmailDto();
		dto.setEmail("myemail.com");

		String json = new Gson().toJson(dto);

		mockMvc.perform(post("/email-confirmation-resends").contentType(MediaType.APPLICATION_JSON).content(json))//
				.andDo(print())//
				.andExpect(status().isBadRequest());
	}
}
