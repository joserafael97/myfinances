package com.openjr.myfinances.controller.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Data;

@Data
public class AuthForm {

	private String email;
	private String password;
	
	public UsernamePasswordAuthenticationToken converter() {		
		return new UsernamePasswordAuthenticationToken(email, password);
	}
}
