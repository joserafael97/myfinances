package com.openjr.myfinances.controller.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.openjr.myfinances.model.Profile;
import com.openjr.myfinances.model.User;

import lombok.Data;

@Data
public class UserFormDto {
	
	private String name;
	private String email;
	private String password;
	
	public User converter(PasswordEncoder passwordEncoder) {
		List<Profile> profiles = new ArrayList<>();
		profiles.add(new Profile(null, "ADMIN"));
		return new User(null, this.name, this.email, passwordEncoder.encode(this.password), true, profiles);
	}

}
