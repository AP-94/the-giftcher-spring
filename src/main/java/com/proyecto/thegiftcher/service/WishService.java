package com.proyecto.thegiftcher.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.proyecto.thegiftcher.domain.Wish;
import com.proyecto.thegiftcher.repository.WishRepository;

public class WishService implements IWishService {
	
	@Autowired
	WishRepository wishRepository;

	@Override
	public Wish get(long id) {
		return wishRepository.findById(id).get();
	}

	@Override
	public List<Wish> getAll() {
		return (List<Wish>) wishRepository.findAll();
	}

	@Override
	public void post(Wish wish) {
		wishRepository.save(wish);
		
	}

	@Override
	public void put(Wish wish, long id) {
		wishRepository.findById(id).ifPresent((x) -> {
			wish.setId(id);
			wishRepository.save(wish);
		});
		
	}

	@Override
	public void delete(long id) {
		wishRepository.deleteById(id);
		
	}

}
