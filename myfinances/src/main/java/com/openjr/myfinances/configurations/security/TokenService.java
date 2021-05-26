package com.openjr.myfinances.configurations.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.openjr.myfinances.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${myfinances.jwt.expiration}")
	private String expiration;

	@Value("${myfinances.jwt.secret}")
	private String secret;

	public String generateToken(Authentication auth) {
		User logger = (User) auth.getPrincipal();
		Date now = new Date();
		Date expirationDate = new Date(now.getTime() + Long.parseLong(expiration));

		return Jwts.builder().setIssuer("My Finances Api").setSubject(logger.getId().toString()).setIssuedAt(now)
				.setExpiration(expirationDate).signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public boolean isValidToken(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUser(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

}
