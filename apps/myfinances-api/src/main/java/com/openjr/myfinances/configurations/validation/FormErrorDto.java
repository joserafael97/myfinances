package com.openjr.myfinances.configurations.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormErrorDto {
	private String field;
	private String message;
}
