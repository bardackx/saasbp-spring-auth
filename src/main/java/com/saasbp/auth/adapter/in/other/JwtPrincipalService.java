package com.saasbp.auth.adapter.in.other;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.saasbp.common.security.Principal;
import com.saasbp.common.security.PrincipalService;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class JwtPrincipalService implements PrincipalService {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private JwtService jwtFactory;

	private Principal principal;

	@PostConstruct
	public void init() {
		String header = request.getHeader("Authorization");
		if (header != null && header.contains("Bearer")) {
			String jwt = header.substring(header.indexOf("Bearer") + "Bearer".length()).trim();
			String subject = jwtFactory.getSubject(jwt);
			System.out.println("subject: "+subject);
			principal = new Principal(UUID.fromString(subject));
		} else {
			principal = new Principal();
		}
	}

	@Override
	public Principal getCaller() {
		return principal;
	}

}
