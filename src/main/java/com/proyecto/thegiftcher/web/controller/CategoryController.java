package com.proyecto.thegiftcher.web.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.proyecto.thegiftcher.repository.CategoryRepository;
import com.proyecto.thegiftcher.web.error.CustomError;
import org.springframework.web.bind.annotation.*;

import com.proyecto.thegiftcher.domain.Category;
import com.proyecto.thegiftcher.service.ICategoryService;

import javax.xml.bind.ValidationException;

@RestController
public class CategoryController {

	private final ICategoryService categoryService;
	private final CategoryRepository categoryRepository;

	public CategoryController(ICategoryService categoryService, CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
		this.categoryService = categoryService;
	}
	
	@GetMapping(path = "/categories")
	public List<Category> getCategories(){
		return categoryService.getAll();
	}

	@GetMapping(path = "/categories/{id}")
	public Category getOne(@PathVariable(value = "id")long id) {
		return categoryService.get(id);
	}

	@PostMapping("/categories")
	public Map<String, String> create(@RequestBody Category category) throws ValidationException {
		String categoryName = category.getCategoryName();
		if (categoryRepository.existsByCategoryName(categoryName)) {
			throw new ValidationException("That category already exists");
		}

		categoryName = category.getCategoryName();

		categoryRepository.save(new Category(categoryName));
		return Collections.singletonMap("message", "true");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PutMapping(path = "/categories/{id}")
	public Map<String, String> updateCategory(@RequestBody Category category) throws Exception {

		Long id = category.getId();
		String categoryName = category.getCategoryName();

		Optional<Category> currentCategory = categoryRepository.findById(id);

		if(!currentCategory.isPresent()){
			throw new Exception("Category not found");
		}

		Category categoryToUpdate = currentCategory.get();

		if (categoryRepository.existsByCategoryName(categoryName)) {
			if (categoryToUpdate.getCategoryName().equals(categoryName)) {
				//
			} else {
				throw new CustomError("That category is already taken");
			}
		}

		categoryToUpdate.setCategoryName(categoryName);
		categoryRepository.save(categoryToUpdate);

		return Collections.singletonMap("message", "Category updated");

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping(path = "/categories/{id}")
	public Map<String, String> delete(@PathVariable long id) throws Exception {

		Optional<Category> currentCategory = categoryRepository.findById(id);

		if (!currentCategory.isPresent()) {
			throw new Exception("Category not found");
		}

		Category categoryToDelete = currentCategory.get();
		categoryRepository.deleteById(categoryToDelete.getId());

		return Collections.singletonMap("message", "Category with id: " + categoryToDelete.getId() + " deleted");

	}
}
