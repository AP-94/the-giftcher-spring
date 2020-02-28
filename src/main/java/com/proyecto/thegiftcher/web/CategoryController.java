package com.proyecto.thegiftcher.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.thegiftcher.domain.Category;
import com.proyecto.thegiftcher.service.ICategoryService;

@RestController
public class CategoryController {
	
	@Autowired
	ICategoryService categoryService;
	
	@GetMapping(path = "/categories")
	public List<Category> getUsers(){
		return categoryService.getAll();
	}

	@GetMapping(path = "/category/{id}")
	public Category getOne(@PathVariable(value = "id")long id) {
		return categoryService.get(id);
	}
}
