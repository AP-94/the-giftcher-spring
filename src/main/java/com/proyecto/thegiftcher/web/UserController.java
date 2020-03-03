package com.proyecto.thegiftcher.web;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	public List<User> getUsers(){
		return userService.getAll();
	}
	
	@GetMapping(path = "/user/{id}")
	public User getOne(@PathVariable(value = "id")long id) {
		return userService.get(id);
	}
	
	 @PostMapping("/user")
	    public Boolean create(@RequestBody Map<String, String> body, @RequestBody Timestamp birthday, @RequestBody Byte profileImage) throws NoSuchAlgorithmException {
	        String username = body.get("username");
	        if (userRepository.existsByUsername(username)){

	            throw new ValidationException("Username already existed");

	        }
	        
	        String name = body.get("name");
	        String lastName = body.get("lastName");
	        String mail = body.get("mail");
	        String password = body.get("password");
	        String encodedPassword = new BCryptPasswordEncoder().encode(password);
//	        String hashedPassword = hashData.get_SHA_512_SecurePassword(password);
	        userRepository.save(new User(username, name, lastName, mail, encodedPassword, birthday, profileImage));
	        return true;
	    }
	
	/*Prueba para ver que todo bien para añadir un usuario (se debe eliminar)
	@PostMapping(path = "/user")
	public void add(User user) {
		userService.post(user);
	}*/
	
	
	@PostMapping(path = "/register")
	public void register(@RequestBody User user) {
		userService.post(user);
	}
	
	@GetMapping(path = "/login")
	public void login(User user) {
		userService.post(user);
	}
	
	
	@PutMapping(path = "/user/{id}")
	public void update(User user, @PathVariable(value = "id")long id) {
		userService.put(user, id);
	}

	@DeleteMapping(path = "/user/{id}")
	public void delete(@PathVariable(value = "id")long id) {
		userService.delete(id);
	}
}
