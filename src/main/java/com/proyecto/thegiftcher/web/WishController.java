package com.proyecto.thegiftcher.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.thegiftcher.domain.Wish;
import com.proyecto.thegiftcher.service.IWishService;

@RestController
public class WishController {

	@Autowired
	IWishService wishService;
	
	@GetMapping(path = "/wishes")
	public List<Wish> getWishes(){
		return wishService.getAll();
	}
	
	@GetMapping(path = "/wish/{id}")
	public Wish getOne(@PathVariable(value = "id")long id) {
		return wishService.get(id);
	}
	
	@PostMapping(path = "/addwish")
	public void addwish(@RequestBody Wish wish) {
		wishService.post(wish);
	}
	
	@PutMapping(path = "/wish/{id}")
	public void update(Wish wish, @PathVariable(value = "id")long id) {
		wishService.put(wish, id);
	}

	@DeleteMapping(path = "/wish/{id}")
	public void delete(@PathVariable(value = "id")long id) {
		wishService.delete(id);
	}
   
}

