package com.openjr.myfinances.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.openjr.myfinances.controller.dto.ExpenseDto;
import com.openjr.myfinances.controller.dto.ExpenseFormDto;
import com.openjr.myfinances.model.Expense;
import com.openjr.myfinances.repository.ExpenseRepository;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	
	@GetMapping
	@Cacheable(cacheNames = "ExpesesList") //to learn use cache to example
	public Page<ExpenseDto> findAll(@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable pageable){
		return ExpenseDto.converter(this.expenseRepository.findAll(pageable));
	}
	
	
	@PostMapping
	@Transactional
	@CacheEvict(cacheNames = "ExpesesList", allEntries = true)
	public ResponseEntity<ExpenseDto> save(@RequestBody @Valid ExpenseFormDto expenseFormDto, UriComponentsBuilder uriComponentsBuilder) {
		Expense expense = expenseRepository.save(expenseFormDto.converter());
		URI uri = uriComponentsBuilder.path("/expenses/{id}").buildAndExpand(expense.getId()).toUri();
		return ResponseEntity.created(uri).body(new ExpenseDto(expense));
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ExpenseDto> findById(@PathVariable Long id) {
		Optional<Expense> expense = this.expenseRepository.findById(id);
		if(expense.isPresent()) {
			return ResponseEntity.ok(new ExpenseDto(expense.get()));
 		}
		return ResponseEntity.notFound().build();
	}

}
