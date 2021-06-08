package om.opejr.common.controller;

import org.springframework.http.MediaType;
import org.springframework.data.domain.Pageable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import om.opejr.common.dto.BaseDto;
import om.opejr.common.exception.BusinessException;
import om.opejr.common.util.NullAwareBeanUtils;

public abstract class CrudController<T, K extends BaseDto<T>, ID> {

	protected static final String PAGE_NUMBER_PARAM = "page";
	protected static final String PAGE_SIZE_PARAM = "size";

	protected JpaRepository<T, ID> repository;
	
	protected final Logger LOGGER = Logger.getLogger(CrudController.class.getName());

	public CrudController(JpaRepository<T, ID> repository) {
		this.repository = repository;
	}

	protected abstract K convert(T entity);

	@GetMapping(params = { PAGE_NUMBER_PARAM, PAGE_SIZE_PARAM })
	public Page<K> getPaginated(@PageableDefault Pageable page, @RequestParam Map<String, String> requestParams) {
		Page<T> entities = this.repository.findAll(page);

		return entities.map(entity -> this.convert(entity));
	}

	@GetMapping
	public List<K> get(@RequestParam Map<String, String> requestParams) {
		List<T> entities = this.repository.findAll();

		return entities.stream().map(entity -> this.convert(entity)).collect(Collectors.toList());
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<K> getById(@PathVariable ID id) {
		Optional<T> optional = this.repository.findById(id);

		if (!optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		K result = this.convert(optional.get());

		return ResponseEntity.ok(result);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<K> post(@RequestBody @Valid K form) throws BusinessException {

		T entity = this.repository.save(form.convert());

		return ResponseEntity.ok(this.convert(entity));
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<K> put(@PathVariable ID id, @RequestBody @Valid K form) throws BusinessException {
		Optional<T> optional = this.repository.findById(id);

		if (optional.isPresent()) {
			T entity = this.repository.save(form.convert());
			return ResponseEntity.ok(this.convert(entity));
		}

		return ResponseEntity.notFound().build();
	}

	@PatchMapping("/{id}")
	@Transactional
	public ResponseEntity<K> patch(@PathVariable ID id, @RequestBody K form) throws BusinessException {
		Optional<T> optional = this.repository.findById(id);

		if (optional.isPresent()) {
			T entity = optional.get();
			try {
				NullAwareBeanUtils.getInstance().copyProperties(entity, form.convert());
			} catch (IllegalAccessException | InvocationTargetException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			entity = this.repository.save(entity);
			return ResponseEntity.ok(this.convert(entity));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable ID id) throws BusinessException {
		Optional<T> optional = this.repository.findById(id);
		if (optional.isPresent()) {
			this.repository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
