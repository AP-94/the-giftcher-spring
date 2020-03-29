package com.proyecto.thegiftcher.service;

import java.util.List;

import com.proyecto.thegiftcher.domain.Category;

public interface ICategoryService {
	
	Category get(long id);
	List<Category> getAll();

	Category getWishByCategoryId(long id) throws Exception;

	void post(Category category);
	void put(Category category, long id);
	void delete(long id);
}
