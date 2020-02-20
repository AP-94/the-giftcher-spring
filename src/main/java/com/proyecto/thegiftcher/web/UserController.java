package com.proyecto.thegiftcher.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.service.IUserService;

@RestController
public class UserController {
	
	@Autowired
	IUserService userService;
	
	@GetMapping(path = "/users")
	public List<User> getUsers(){
		return userService.getAll();
	}
	
	@GetMapping(path = "/user/{id}")
	public User getOne(@PathVariable(value = "id")long id) {
		return userService.get(id);
	}
	
	@PostMapping(path = "/user")
	public void add(User user) {
		userService.post(user);
	}
	
	@PutMapping(path = "/user")
	public void update(User user, long id) {
		userService.put(user, id);
	}

}
