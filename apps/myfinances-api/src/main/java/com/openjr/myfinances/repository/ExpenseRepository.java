package com.openjr.myfinances.repository;

import com.openjr.myfinances.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
