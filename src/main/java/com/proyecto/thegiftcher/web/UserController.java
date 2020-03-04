package com.proyecto.thegiftcher.web;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.repository.UserRepository;
import com.proyecto.thegiftcher.service.IUserService;

@RestController
public class UserController {

	@Autowired
	IUserService userService;

	@Autowired
	UserRepository userRepository;

	@GetMapping(path = "/users")
	public List<User> getUsers() {
		return userService.getAll();
	}

	@GetMapping(path = "/user/{id}")
	public User getOne(@PathVariable(value = "id") long id) {
		return userService.get(id);
	}

	@PostMapping("/register")
	public Boolean create(@RequestBody User user) throws NoSuchAlgorithmException {
		String username = user.getUsername();
		if (userRepository.existsByUsername(username)) {
			throw new ValidationException("Username already existed");
		}

		String name = user.getName();
		String lastName = user.getLastName();
		String mail = user.getMail();
		String password = user.getPassword();
		String encodedPassword = new BCryptPasswordEncoder().encode(password);
		Timestamp birthday = user.getBirthday();
		Byte profileImage = user.getProfileImage();
		userRepository.save(new User(username, name, lastName, mail, encodedPassword, birthday, profileImage));
		return true;
	}

	@PutMapping(path = "/user/{id}")
	public void update(User user, @PathVariable(value = "id") long id) {
		userService.put(user, id);
	}

	@DeleteMapping(path = "/user/{id}")
	public void delete(@PathVariable(value = "id") long id) {
		userService.delete(id);
	}
}
