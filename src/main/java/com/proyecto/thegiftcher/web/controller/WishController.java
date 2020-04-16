package com.proyecto.thegiftcher.web.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.thegiftcher.domain.Wish;
import com.proyecto.thegiftcher.service.IWishService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(
		value = "/wishes"
)
public class WishController {

	private final IWishService wishService;

	public WishController(IWishService wishService) {
		this.wishService = wishService;
	}
	
	@GetMapping(path = "/")
	public ResponseEntity<List<Wish>> getWishes(HttpServletRequest request){
		List<Wish> wishes = wishService.getAll(request);
		return new ResponseEntity<>(wishes, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Wish> getOne(@PathVariable long id, HttpServletRequest request) throws Exception {
		Wish wish = wishService.get(id, request);

		return new ResponseEntity<>(wish, HttpStatus.OK);
	}

	@GetMapping(path = "/categories/{categoryId}")
	public ResponseEntity<List<Wish>> getWishByCategory(@PathVariable long categoryId) throws Exception {
		List<Wish> wish = wishService.getWishByCategoryId(categoryId);

		return new ResponseEntity<>(wish, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = "/")
	public Map<String, String> addwish(@RequestBody Wish wish, HttpServletRequest request) {
		wishService.create(wish, request);
		return Collections.singletonMap("message", "New wish added successfully");
	}

	@PostMapping(path = "/copy/userId/{userId}/id/{id}")
	public Map<String, String> copyWishFromUser(@PathVariable long userId, @PathVariable long id, HttpServletRequest request) {
		wishService.copyWishFromUser(userId, id, request);

		return Collections.singletonMap("message", "true");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping(path = "/{id}")
	public Map<String, String> update(@RequestBody Wish wish, @PathVariable long id, HttpServletRequest request) {
		wishService.modify(wish, id, request);

		return Collections.singletonMap("message", "Wish with name " + wish.getName() + " modifyed");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DeleteMapping(path = "/{id}")
	public Map<String, String> delete(@PathVariable long id, HttpServletRequest request) {
		wishService.delete(id, request);

		return Collections.singletonMap("message", "Wish with id: " + id + " deleted");
	}
	
	@PutMapping("/wish_images/{id}")
	public Map<String, String> setImage(@PathVariable(value = "id") long id,
										@RequestParam("file") MultipartFile file, HttpServletRequest request)
			throws Exception {
		
		wishService.addImages(id, file, request);
		return Collections.singletonMap("message", "true");
	}
   
}

