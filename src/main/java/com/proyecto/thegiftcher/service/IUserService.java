package com.proyecto.thegiftcher.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import com.proyecto.thegiftcher.domain.Password;
import com.proyecto.thegiftcher.domain.User;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {
	
	User get(long id);
	User findUserByMail(String email);
	User getByUsername(String username);
	User getUserLogged(HttpServletRequest request);
	List<User> getAll();
	Resource loadProfileImageAsResource(long id);
	void post(User user);
	void put(User user, long id);
	void updateUser(User user);
	void resetPassword(String userMail) throws Exception;
	void delete(long id) throws Exception;
	void updateUserPassword(Password password, long id) throws Exception;
	void updateUser(User user, long id) throws Exception;
	void profileImage(MultipartFile file, long id) throws Exception;
	void registerUser(User user) throws NoSuchAlgorithmException;

}
