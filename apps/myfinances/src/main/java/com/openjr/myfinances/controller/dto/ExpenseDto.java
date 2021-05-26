package com.openjr.myfinances.controller.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.openjr.myfinances.model.Expense;

import lombok.Data;

@Data
public class ExpenseDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private LocalDate date;
	private BigDecimal value;
	
	public ExpenseDto(Expense expense) {
		this.id = expense.getId();
		this.name = expense.getName();
		this.date = expense.getDate();
		this.value = expense.getValue();
	}
	
	public static Page<ExpenseDto> converter(Page<Expense> expenses) {
		return expenses.map(ExpenseDto::new);
	}
}
