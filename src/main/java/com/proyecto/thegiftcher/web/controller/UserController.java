package com.proyecto.thegiftcher.web.controller;

import com.proyecto.thegiftcher.domain.Password;
import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	private final IUserService userService;

	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@GetMapping(path = "/get_users")
	public List<User> getUsers() {
		return userService.getAll();
	}

	@GetMapping(path = "/{id}")
	public User getOne(@PathVariable(value = "id") long id) {
		return userService.get(id);
	}
	
	@PostMapping(path = "/register")
	public Boolean create(@RequestBody User user) throws NoSuchAlgorithmException {
		userService.registerUser(user);
		return true;
	}
	
	@PutMapping(path = "/profile_image")
	public Boolean setImage(@RequestParam("file") MultipartFile file, HttpServletRequest request)
			throws Exception {
		User user = userService.getUserLogged(request);
		Long id = user.getId();
		userService.profileImage(file, id);
		
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PutMapping(path = "/update")
	public ResponseEntity updateUser(@RequestBody User user, HttpServletRequest request) throws Exception {
		
		User userLogged = userService.getUserLogged(request);
		Long id = userLogged.getId();
		userService.updateUser(user, id);
		
		return new ResponseEntity("User updated", HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PutMapping(path = "/update_password")
	public ResponseEntity updateUserPassword(@RequestBody Password password, HttpServletRequest request) throws Exception {
		User user = userService.getUserLogged(request);
		Long id = user.getId();
		userService.updateUserPassword(password, id);
		
		return new ResponseEntity("User password updated", HttpStatus.OK);
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping(path = "/delete_account")
	public ResponseEntity delete(HttpServletRequest request) throws Exception {
		User user = userService.getUserLogged(request);
		userService.delete(user.getId());
		
		return new ResponseEntity("User with id: " + user.getId() + " deleted", HttpStatus.OK);
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(path = "/reset_password")
	public ResponseEntity resetPassword(@RequestParam String userMail) throws Exception {
		userService.resetPassword(userMail);
		
		return new ResponseEntity("Mail successfully delivered", HttpStatus.OK);
		
	}
}
