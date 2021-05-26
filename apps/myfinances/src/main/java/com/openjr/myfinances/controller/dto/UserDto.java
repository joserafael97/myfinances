package com.openjr.myfinances.controller.dto;

import com.openjr.myfinances.model.User;

import lombok.Data;

@Data
public class UserDto {
	
	private Long id;
	private String name;
	private String email;
	private boolean status;
	
	public UserDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.status = user.isStatus();
	}
	

	

}
