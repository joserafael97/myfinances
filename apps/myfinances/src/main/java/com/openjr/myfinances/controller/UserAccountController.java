package com.openjr.myfinances.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.openjr.myfinances.controller.dto.UserDto;
import com.openjr.myfinances.controller.dto.UserFormDto;
import com.openjr.myfinances.model.User;
import com.openjr.myfinances.repository.UserRepository;

@RestController()
@RequestMapping("/account-user")
public class UserAccountController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping
	@Transactional
	public ResponseEntity<UserDto> save(@RequestBody @Valid UserFormDto userFormDto, UriComponentsBuilder uriComponentsBuilder) {
		User user = userRepository.save(userFormDto.converter(this.passwordEncoder));
		URI uri = uriComponentsBuilder.path("/account-user/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(new UserDto(user));
	}
	
	
	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}

}
