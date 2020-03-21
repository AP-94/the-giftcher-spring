package com.proyecto.thegiftcher.service;

import java.util.List;

import com.proyecto.thegiftcher.domain.Category;

public interface ICategoryService {
	
	Category get(long id);
	List<Category> getAll();

}
