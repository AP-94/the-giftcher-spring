package com.proyecto.thegiftcher.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
		if (CollectionUtils.isEmpty(wishes)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(wishes, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Wish> getOne(@PathVariable long id, HttpServletRequest request) throws Exception {
		Wish wish = wishService.get(id, request);

		return new ResponseEntity<>(wish, HttpStatus.OK);
	}
	
	@PostMapping(path = "/")
	public void addwish(@RequestBody Wish wish, HttpServletRequest request) {
		wishService.create(wish, request);
	}

	@PostMapping(path = "/copy/userId/{userId}/id/{id}")
	public void copyWishFromUser(@PathVariable long userId, @PathVariable long id, HttpServletRequest request) {
		wishService.copyWishFromUser(userId, id, request);
	}
	
	@PutMapping(path = "/{id}")
	public void update(@RequestBody Wish wish, @PathVariable long id, HttpServletRequest request) {
		wishService.modify(wish, id, request);
	}

	@DeleteMapping(path = "/{id}")
	public void delete(@PathVariable long id, HttpServletRequest request) {
		wishService.delete(id, request);
	}
   
}

