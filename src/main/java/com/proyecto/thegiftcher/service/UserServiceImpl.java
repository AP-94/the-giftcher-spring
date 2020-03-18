package com.proyecto.thegiftcher.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	UserRepository userRepository;

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

}
