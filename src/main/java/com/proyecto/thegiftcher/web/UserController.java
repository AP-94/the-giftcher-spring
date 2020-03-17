package com.proyecto.thegiftcher.web;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.repository.UserRepository;
import com.proyecto.thegiftcher.service.IUserService;
import com.proyecto.thegiftcher.web.error.CustomError;

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
			throw new ValidationException("That username is already taken");
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(path = "/user/update_user")
	public ResponseEntity updateUser(@RequestBody User user) throws Exception {
		
		Long id = user.getId();
		String username = user.getUsername();
		String name = user.getName();
		String lastName = user.getLastName();
		
		if (userRepository.existsByUsername(username)) {
			throw new CustomError("That username is already taken");
		}
		
		Optional<User> currentUser = userRepository.findById(id);
		
		if (currentUser == null) {
			throw new Exception("User not found");
		} 
		
		User userToUpdate = currentUser.get();
		
		if (!name.isEmpty() || name != null) {
			userToUpdate.setName(name);
		}
		if (!lastName.isEmpty() || lastName != null) {
			userToUpdate.setLastName(lastName);
		}
		if (!username.isEmpty() || username != null) {
			userToUpdate.setUsername(username);
		}
		userRepository.save(userToUpdate);
		
		return new ResponseEntity("User updated", HttpStatus.OK);
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(path = "/user/update_password")
	public ResponseEntity updateUserPassword(@RequestBody User user) throws Exception {
		
		Long id = user.getId();
		String password = user.getPassword();
		
		Optional<User> currentUser = userRepository.findById(id);
		
		if (currentUser == null) {
			throw new Exception("User not found");
		} 
		
		String encodedPassword = new BCryptPasswordEncoder().encode(password);
		User userToUpdate = currentUser.get();
		userToUpdate.setPassword(encodedPassword);
		userRepository.save(userToUpdate);
		
		return new ResponseEntity("User password updated", HttpStatus.OK);
		
	}
	
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(path = "/user/update_password")
	public ResponseEntity updateProfileImage(@RequestBody User user) throws Exception {
		
		Long id = user.getId();
		
		
		Optional<User> currentUser = userRepository.findById(id);
		
		if (currentUser == null) {
			throw new Exception("User not found");
		} 
		
		User userToUpdate = currentUser.get();
		userToUpdate.setProfileImage(profileImage);
		userRepository.save(userToUpdate);
		
		return new ResponseEntity("User profile image updated", HttpStatus.OK);
		
	}*/

	@DeleteMapping(path = "/user/{id}")
	public void delete(@PathVariable(value = "id") long id) {
		userService.delete(id);
	}
}
