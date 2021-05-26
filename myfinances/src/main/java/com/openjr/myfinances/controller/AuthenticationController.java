package com.openjr.myfinances.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openjr.myfinances.configurations.security.TokenService;
import com.openjr.myfinances.controller.dto.AuthForm;
import com.openjr.myfinances.controller.dto.TokenDto;

@RestController()
@RequestMapping("/auth")
@Profile(value = {"prod", "test"})
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<TokenDto> authenticate(@RequestBody @Valid AuthForm form) {
		UsernamePasswordAuthenticationToken userAuthToken = form.converter();
		Authentication auth = this.authManager.authenticate(userAuthToken);
		String token = tokenService.generateToken(auth);
		return ResponseEntity.ok(new TokenDto(token, "Bearer"));
	}
}
