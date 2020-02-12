package com.saasbp.auth.adapter.in.other;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService {

	byte[] key = "banana".getBytes();

	public String generateJwt(String subject) {

		String issuer = "saasbp.bardackx.com";
		Date now = Date.from(Instant.now());
		Date expiration = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
		SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;

		String jwt = Jwts.builder() //
				.setIssuer(issuer) //
				.setIssuedAt(now) //
				.setSubject(subject) //
				.setExpiration(expiration) //
				.signWith(algorithm, key) //
				.compact();

		return jwt;
	}

	public String getSubject(String jwt) {
		Jws<Claims> parse = Jwts.parser() //
				.setSigningKey(key) //
				.parseClaimsJws(jwt);		
		return parse.getBody().getSubject();
	}
}
