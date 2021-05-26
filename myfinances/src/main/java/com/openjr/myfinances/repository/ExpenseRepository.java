package com.openjr.myfinances.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openjr.myfinances.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
