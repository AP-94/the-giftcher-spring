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

import javax.validation.ValidationException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.zip.Deflater;

@RestController
public class UserController {

	private final IUserService userService;
	private final UserRepository userRepository;
	public static String profileImagesDirectory = System.getProperty("user.dir") + "/profileImages";

	public UserController(IUserService userService, UserRepository userRepository) {
		this.userRepository = userRepository;
		this.userService = userService;
	}

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
	
	@PutMapping("/profile_image/{id}")
	public Boolean setImage(@PathVariable(value = "id") long id, @RequestParam("file") MultipartFile file) throws Exception {
		
		String imageName = file.getOriginalFilename();
		String imagePath = Paths.get(profileImagesDirectory, imageName).toString();
		
		Optional<User> currentUser = userRepository.findById(id);
		
		if(!currentUser.isPresent()){
			throw new Exception("User not found");
		} 
		
		//Save the file locally
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(imagePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stream.write(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User userToUpdate = currentUser.get();
		
		if (!userToUpdate.getImagePath().isEmpty()) {
			userService.deleteFile(userToUpdate.getImageName());
		}
		
		userToUpdate.setImageName(imageName);
		userToUpdate.setImagePath(imagePath);
		userRepository.save(userToUpdate);
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PutMapping(path = "/user/update_user")
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
	@PutMapping(path = "/user/update_password")
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
	
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(path = "/user/update_image")
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping(path = "/user/{id}")
	public ResponseEntity delete(@PathVariable long id) throws Exception {
		
		Optional<User> currentUser = userRepository.findById(id);
		
		if (!currentUser.isPresent()) {
			throw new Exception("User not found");
		} 
		
		User userToDelete = currentUser.get();
		userRepository.deleteById(userToDelete.getId());
		
		return new ResponseEntity("User with id: " + userToDelete.getId() + " deleted", HttpStatus.OK);
		
	}
}
