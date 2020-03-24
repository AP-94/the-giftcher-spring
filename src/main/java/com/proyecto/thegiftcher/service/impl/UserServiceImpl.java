package com.proyecto.thegiftcher.service.impl;

import java.io.File;
import java.util.List;

import com.proyecto.thegiftcher.config.JwtTokenUtil;
import com.proyecto.thegiftcher.service.IUserService;
import org.springframework.stereotype.Service;

import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements IUserService {

	private final UserRepository userRepository;
	private final JwtTokenUtil jwtTokenUtil;
	public static String profileImagesDirectory = System.getProperty("user.dir") + "/profileImages";

	public UserServiceImpl(UserRepository userRepository, JwtTokenUtil jwtTokenUtil) {
		this.userRepository = userRepository;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	public User get(long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public List<User> getAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public void post(User user) {
		userRepository.save(user);
		
	}

	@Override
	public void put(User user, long id) {
		userRepository.findById(id).ifPresent((x) -> {
			user.setId(id);
			userRepository.save(user);
		});
		
	}
	
	@Override
	public void updateUser(User user) {
		if (userRepository.existsByUsername(user.getUsername())) {
			user.setId(user.getId());
			userRepository.save(user);
		}
	}

	@Override
	public void delete(long id) {
		userRepository.deleteById(id);
		
	}

	@Override
	public User getByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User getUserLogged(HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = requestTokenHeader.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		return userRepository.findByUsername(username);
	}
	
	@Override
	public Boolean deleteFile(String file) {
		boolean status = false;
		try {
			if (file != null) {
				File fileToDelete = new File(file);
				status = fileToDelete.delete();
				return status;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return status;
			
		}
		return status;
	}
}
