package com.proyecto.thegiftcher.service.impl;

import java.util.List;
import com.proyecto.thegiftcher.service.ICategoryService;
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

	@Override
	public Category getWishByCategoryId(long id) throws Exception {
		return null;
	}

	@Override
	public void post(Category category) {
		categoryRepository.save(category);

	}

	@Override
	public void put(Category category, long id) {
		categoryRepository.findById(id).ifPresent((x) -> {
			category.setId(id);
			categoryRepository.save(category);
		});

	}

	@Override
	public void delete(long id) {
		categoryRepository.deleteById(id);

	}

}
