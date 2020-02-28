package com.proyecto.thegiftcher.service;

import java.util.List;

import com.proyecto.thegiftcher.domain.Category;

public interface ICategoryService {
	
	public Category get(long id);
	public List<Category> getAll();

}
