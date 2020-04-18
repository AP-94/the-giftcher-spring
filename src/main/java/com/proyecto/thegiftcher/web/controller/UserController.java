package com.proyecto.thegiftcher.web.controller;

import com.proyecto.thegiftcher.domain.Password;
import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.service.IUserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
	public Map<String, String> create(@RequestBody User user) throws NoSuchAlgorithmException {
		userService.registerUser(user);
		return Collections.singletonMap("message", "true");
	}
	
	@PutMapping(path = "/profile_image")
	public Map<String, String> setImage(@RequestParam("file") MultipartFile file, HttpServletRequest request)
			throws Exception {
		User user = userService.getUserLogged(request);
		Long id = user.getId();
		userService.profileImage(file, id);

		return Collections.singletonMap("message", "true");
	}
	
	@PutMapping(path = "/update")
	public Map<String, String> updateUser(@RequestBody User user, HttpServletRequest request) throws Exception {
		
		User userLogged = userService.getUserLogged(request);
		Long id = userLogged.getId();
		userService.updateUser(user, id);

		return Collections.singletonMap("message", "User updated");
	}
	
	@PutMapping(path = "/update_password")
	public Map<String, String> updateUserPassword(@RequestBody Password password, HttpServletRequest request) throws Exception {
		User user = userService.getUserLogged(request);
		Long id = user.getId();
		userService.updateUserPassword(password, id);

		return Collections.singletonMap("message", "User password updated");
		
	}

	@DeleteMapping(path = "/delete_account")
	public Map<String, String> delete(HttpServletRequest request) throws Exception {
		User user = userService.getUserLogged(request);
		userService.delete(user.getId());

		return Collections.singletonMap("message", "User with id: " + user.getId() + " deleted");
		
	}
	
	@PostMapping(path = "/reset_password")
	public Map<String, String> resetPassword(@RequestParam String userMail) throws Exception {
		userService.resetPassword(userMail);

		return Collections.singletonMap("message", "Mail successfully delivered");
		
	}
}
