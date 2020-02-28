package com.proyecto.thegiftcher.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.thegiftcher.domain.Category;
import com.proyecto.thegiftcher.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements ICategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Override
	public Category get(long id) {
		return categoryRepository.findById(id).get();
	}

	@Override
	public List<Category> getAll() {
		return (List<Category>) categoryRepository.findAll();
	}

}
