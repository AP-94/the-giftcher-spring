package com.proyecto.thegiftcher.web;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.thegiftcher.config.JwtTokenUtil;
import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.service.IUserService;
import com.proyecto.thegiftcher.service.Impl.JwtUserDetailsServiceImpl;

@RestController
@CrossOrigin
public class AuthController {

	private final static Logger LOGGER = Logger.getLogger("JwtAuthenticationController");
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsServiceImpl jwtUserDetailsServiceImpl;
	@Autowired
	private IUserService userService;
	 

	@PostMapping(value = "/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody User authenticationRequest) throws Exception {
		LOGGER.log(Level.INFO,
				"******** " + authenticationRequest.getUsername() + " " + authenticationRequest.getPassword());
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		UserDetails userDetails = jwtUserDetailsServiceImpl.loadUserByUsername(authenticationRequest.getUsername());
		User user = userService.getByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		user.setToken(token);
		
		return ResponseEntity.ok(new User(user.getId(), user.getName(), user.getLastName(), user.getUsername(), user.getMail(), user.getBirthday(), user.getProfileImage(), user.getToken()));
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
