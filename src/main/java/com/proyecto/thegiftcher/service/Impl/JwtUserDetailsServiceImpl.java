package com.proyecto.thegiftcher.service.Impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.repository.UserRepository;

@Component
public class JwtUserDetailsServiceImpl implements UserDetailsService{
	
	 	@Autowired
	    private UserRepository userRepository;


	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	        User user = userRepository.findByUsername(username);
	        if (user == null) {
	            throw new UsernameNotFoundException("Error with User logged, user not found with username: " + username);
	        }
	        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
	                new ArrayList<>());
	    }

}