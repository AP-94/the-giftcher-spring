package com.proyecto.thegiftcher.service;

import java.util.List;

import com.proyecto.thegiftcher.domain.Wish;

import javax.servlet.http.HttpServletRequest;

public interface IWishService {

	List<Wish> getAll(HttpServletRequest userId);
	Wish get(long id, HttpServletRequest request) throws Exception;

	List<Wish> getWishByCategoryId(long categoryId) throws Exception;

	void create(Wish wish, HttpServletRequest request);
	void copyWishFromUser(long userId, long id, HttpServletRequest request);
	void modify(Wish wish, long id, HttpServletRequest request);
	void delete(long id, HttpServletRequest request);
}
