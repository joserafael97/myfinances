package com.openjr.myfinances.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import com.openjr.myfinances.model.Expense;

@DataJpaTest
@ActiveProfiles("test")
public class ExpenseRepositoryTest {
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private TestEntityManager em;
	
	@Test
	public void findByIdCursoTest() {
		Expense expense = new Expense(null, "Teste", LocalDate.now(), BigDecimal.ONE);
		em.persist(expense);
		Assert.isTrue(this.expenseRepository.findById(1l).isPresent(), "Object Exists!");
	}

}
