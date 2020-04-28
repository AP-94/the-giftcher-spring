package com.proyecto.thegiftcher.web.controller;

import com.proyecto.thegiftcher.domain.Password;
import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.service.IUserService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	public User registerUser(@RequestBody User user) throws Exception {
		return userService.register(user);
	}

	@PostMapping(value = "/login")
	public User loginUser(@RequestBody User authenticationRequest) throws Exception {
		return userService.login(authenticationRequest);
	}
	
	@PutMapping(path = "/update")
	public User updateUser(@RequestBody User user, HttpServletRequest request) throws Exception {
		User userLogged = userService.getUserLogged(request);
		Long id = userLogged.getId();

		return userService.updateUser(user, id);
	}

	@PutMapping(path = "/update_password")
	public Map<String, String> updateUserPassword(@RequestBody Password password, HttpServletRequest request)
			throws Exception {
		User user = userService.getUserLogged(request);
		Long id = user.getId();
		userService.updateUserPassword(password, id);

		return Collections.singletonMap("message", "User password updated");

	}

	// EN USO, la tercera es la vencida, imagen con google cloud storage
	@PostMapping(path = "/google_cloud_image")
	public User uploadToGCP(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
		User user = userService.getUserLogged(request);
		Long id = user.getId();

		return userService.profileImageGoogleCloud(file, id);
	}

	@DeleteMapping(path = "/delete_account")
	public Map<String, String> delete(HttpServletRequest request) throws Exception {
		User user = userService.getUserLogged(request);
		userService.delete(user.getId());

		return Collections.singletonMap("message", "User: " + user.getUsername() + " deleted");

	}

	@PostMapping(path = "/reset_password")
	public Map<String, String> resetPassword(@RequestParam String userMail) throws Exception {
		userService.resetPassword(userMail);

		return Collections.singletonMap("message", "Mail successfully delivered");

	}

	// NO EN USO
	// Envía la imagen completa, se recibe, se guarda y se puede ver la imagen.
	@GetMapping(path = "/get_profile_image_2")
	public ResponseEntity<Resource> getProfileImage(HttpServletRequest request) throws Exception {
		User user = userService.getUserLogged(request);
		Long id = user.getId();

		Resource file = userService.loadProfileImageAsResource(id);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + user.getImageName() + "\"")
				.body(file);
	}

	// NO EN USO
	@PutMapping(path = "/profile_image_2")
	public Map<String, String> setImage(@RequestParam("file") MultipartFile file, HttpServletRequest request)
			throws Exception {
		User user = userService.getUserLogged(request);
		Long id = user.getId();
		userService.profileImage(file, id);

		return Collections.singletonMap("message", "Profile Image updated");
	}

	// NO EN USO
	@GetMapping(path = "/profile_image_1", produces = MediaType.IMAGE_JPEG_VALUE)
	public void getProfileImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = userService.getUserLogged(request);
		String imagePath = user.getImagePath();

		Path file = Paths.get(imagePath);
		Resource imagFile = new UrlResource(file.toUri());

		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(imagFile.getInputStream(), response.getOutputStream());
	}
}
