package com.proyecto.thegiftcher.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.proyecto.thegiftcher.domain.Wish;

public interface IWishService {

	List<Wish> getAll(HttpServletRequest userId);
	List<Wish> getAll();
	Wish get(long id, HttpServletRequest request) throws Exception;
	List<Wish> getUserWishes(long userId);
	List<Wish> getWishByCategoryId(long categoryId) throws Exception;
	Wish wishImageGoogleCloud(MultipartFile file, long id, HttpServletRequest request) throws Exception;
	Wish create(Wish wish, HttpServletRequest request);
	Wish copyWishFromUser(long userId, long id, HttpServletRequest request) throws Exception;
	Wish modify(Wish wish, long id, HttpServletRequest request) throws Exception;
	void delete(long id, HttpServletRequest request);
	void addImages(long id, MultipartFile file, HttpServletRequest request) throws Exception;


}
