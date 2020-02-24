package com.proyecto.thegiftcher.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService{

	//Bcrypt hash online: https://www.javainuse.com/onlineBcrypt
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("user1".equals(username)) {
			return new User("user1", 
					"$2a$10$Y/HF7cEQdpfpk8yDJhSM.uHESUcwlaXTOslxIz2A4eq.jvX8KFUOC",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("No existe ningún usuario con este username: " + username);
		}
	}

}
