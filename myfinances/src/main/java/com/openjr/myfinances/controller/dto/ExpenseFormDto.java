package com.openjr.myfinances.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.openjr.myfinances.model.Expense;

import lombok.Data;


@Data
public class ExpenseFormDto {
	private String name;
	private LocalDate date;
	private BigDecimal value;
	
	public Expense converter() {
		return new Expense(null, this.name, this.date, this.value);
	}
}
