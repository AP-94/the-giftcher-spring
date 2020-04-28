package com.proyecto.thegiftcher.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.thegiftcher.domain.Password;
import com.proyecto.thegiftcher.domain.User;

public interface IUserService {
	
	User get(long id);
	User findUserByMail(String email);
	User register(User user) throws Exception;
	User login(User user) throws Exception;
	User getByUsername(String username);
	User getUserLogged(HttpServletRequest request);
	User updateUser(User user, long id) throws Exception;
	User profileImageGoogleCloud(MultipartFile file, long id) throws Exception;
	List<User> getAll();
	Resource loadProfileImageAsResource(long id);
	void post(User user);
	void put(User user, long id);
	void updateUser(User user) throws Exception;
	void resetPassword(String userMail) throws Exception;
	void delete(long id) throws Exception;
	void updateUserPassword(Password password, long id) throws Exception;
	void profileImage(MultipartFile file, long id) throws Exception;
	void registerUser(User user) throws NoSuchAlgorithmException;

}
