package com.proyecto.thegiftcher.web.controller;

import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.repository.UserRepository;
import com.proyecto.thegiftcher.service.IUserService;
import com.proyecto.thegiftcher.web.error.CustomError;
import com.proyecto.thegiftcher.web.error.UnauthorizedError;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(
		value = "/user"
)
public class UserController {

	private final IUserService userService;
	private final UserRepository userRepository;
	public static String profileImagesDirectory = System.getProperty("user.dir") + "/profileImages";

	public UserController(IUserService userService, UserRepository userRepository) {
		this.userRepository = userRepository;
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
		Date birthday = user.getBirthday();
		
		userRepository.save(new User(username, name, lastName, mail, encodedPassword, birthday));
		return true;
	}
	
	@PutMapping("/profile_image")
	public Boolean setImage(@RequestParam("file") MultipartFile file, HttpServletRequest request)
			throws Exception {
		User user = userService.getUserLogged(request);
		String imageOriginalName = file.getOriginalFilename();
		String imageExtension = imageOriginalName.substring(imageOriginalName.lastIndexOf(".") + 1);
		String imageName = "profile_picture_" + user.getId() + "." + imageExtension;
		String imagePath = Paths.get(profileImagesDirectory, imageName).toString();
		long size = file.getSize();

		if (size > 5000000) {
			throw new Exception("The size of the image is to big");
		}
		
		// Save the file locally
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(imagePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			stream.write(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Optional<User> currentUser = userRepository.findById(user.getId());
		
		if (!currentUser.isPresent()) {
			throw new Exception("User not found");
		}
		
		User userToUpdate = currentUser.get();
		
		userToUpdate.setImageName(imageName);
		userToUpdate.setImagePath(imagePath);
		userRepository.save(userToUpdate);
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PutMapping(path = "/update")
	public ResponseEntity updateUser(@RequestBody User user) throws Exception {
		
		Long id = user.getId();
		String username = user.getUsername();
		String name = user.getName();
		String lastName = user.getLastName();
		
		Optional<User> currentUser = userRepository.findById(id);
		
		if(!currentUser.isPresent()){
			throw new Exception("User not found");
		} 
		
		User userToUpdate = currentUser.get();
		
		if (userRepository.existsByUsername(username)) {
			if (userToUpdate.getUsername().equals(username)) {
				//
			} else {
				throw new CustomError("That username is already taken");
			}
		}
		
		userToUpdate.setName(name);
		userToUpdate.setLastName(lastName);
		userToUpdate.setUsername(username);
		userRepository.save(userToUpdate);
		
		return new ResponseEntity("User updated", HttpStatus.OK);
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PutMapping(path = "/update_password")
	public ResponseEntity updateUserPassword(@RequestBody User user, String oldPassword) throws Exception {
		
		Long id = user.getId();
		String newPassword = user.getPassword();
		
		Optional<User> currentUser = userRepository.findById(id);
		
		if (!currentUser.isPresent()) {
			throw new Exception("User not found");
		} 
		User userToUpdate = currentUser.get();
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
		if (!encoder.matches(oldPassword, userToUpdate.getPassword())) {
			throw new UnauthorizedError("The passwords don't match");
		}
		
		String encodedPassword = new BCryptPasswordEncoder().encode(newPassword);
		userToUpdate.setPassword(encodedPassword);
		userRepository.save(userToUpdate);
		
		return new ResponseEntity("User password updated", HttpStatus.OK);
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping(path = "/delete_user")
	public ResponseEntity delete(HttpServletRequest request) throws Exception {
		
		User user = userService.getUserLogged(request);
		
		Optional<User> currentUser = userRepository.findById(user.getId());
		
		if (!currentUser.isPresent()) {
			throw new Exception("User not found");
		} 
		
		User userToDelete = currentUser.get();
		userRepository.deleteById(userToDelete.getId());
		
		return new ResponseEntity("User with id: " + userToDelete.getId() + " deleted", HttpStatus.OK);
		
	}
}
