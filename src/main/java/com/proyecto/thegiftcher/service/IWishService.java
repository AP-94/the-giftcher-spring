package com.proyecto.thegiftcher.service;

import java.util.List;

import com.proyecto.thegiftcher.domain.Wish;

public interface IWishService {
	
	public Wish get(long id);
	public List<Wish> getAll();
	public void post(Wish wish);
	public void put(Wish wish, long id);
	public void delete(long id);

}
