package com.proyecto.thegiftcher.service;

import java.io.File;
import java.util.List;

import com.proyecto.thegiftcher.domain.User;

import javax.servlet.http.HttpServletRequest;

public interface IUserService {
	
	User get(long id);
	User getByUsername(String username);
	User getUserLogged(HttpServletRequest request);
	List<User> getAll();
	void post(User user);
	void put(User user, long id);
	void updateUser(User user);
	void delete(long id);
	public Boolean deleteFile(String file);

}
