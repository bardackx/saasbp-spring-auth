package com.saasbp.auth.adapter.out.persistence.tests;

import static org.junit.Assert.assertTrue;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.saasbp.auth.application.port.out.FindEmailResetByCode;
import com.saasbp.auth.domain.EmailReset;

@DBRider
@SpringBootTest
public class EmailResetIntegrationTest {

	@Autowired
	private FindEmailResetByCode findEmailResetByCode;

	@Test
	@DataSet({ "persistence/find-email-reset-by-code.yml" })
	public void requestEmailReset() throws Exception {
		UUID code = UUID.fromString("2cb6932d-aab0-4e43-b082-e8395a3c8ed6");
		Optional<EmailReset> optional = findEmailResetByCode.findEmailResetByCode(code);
		assertTrue(optional.isPresent());
	}

}
