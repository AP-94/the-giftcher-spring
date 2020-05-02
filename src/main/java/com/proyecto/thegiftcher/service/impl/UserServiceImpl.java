package com.proyecto.thegiftcher.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.proyecto.thegiftcher.config.JwtTokenUtil;
import com.proyecto.thegiftcher.service.IEmailService;

import com.proyecto.thegiftcher.service.IUserService;
import com.proyecto.thegiftcher.web.error.CustomError;
import com.proyecto.thegiftcher.web.error.StorageFileNotFoundException;
import com.proyecto.thegiftcher.web.error.UnauthorizedError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.thegiftcher.domain.Password;
import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

@Service
public class UserServiceImpl implements IUserService {

	private final UserRepository userRepository;
	private final JwtTokenUtil jwtTokenUtil;
	private final IEmailService emailService;
	public static String profileImagesDirectory = "/home/ec2-user/profileImages";
	private final static Logger LOGGER = Logger.getLogger("JwtAuthenticationController");
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUserDetailsServiceImpl jwtUserDetailsServiceImpl;
	
	public UserServiceImpl(UserRepository userRepository, JwtTokenUtil jwtTokenUtil, IEmailService emailService) throws IOException {
		this.userRepository = userRepository;
		this.jwtTokenUtil = jwtTokenUtil;
		this.emailService = emailService;
	}

	@Override
	public User get(long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public User findUserByMail(String email) {
		return userRepository.findByMail(email);
	}
	
	@Override
	public User register(User user) throws Exception {
		registerUser(user);
		User completeUser = getByUsername(user.getUsername());
		
		LOGGER.log(Level.INFO,
				"******** " + user.getUsername() + " " + user.getPassword());
		authenticate(user.getUsername(), user.getPassword());
		UserDetails userDetails = jwtUserDetailsServiceImpl.loadUserByUsername(user.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		completeUser.setToken(token);
		
		return completeUser;
	}
	
	@Override
	public User login(User authenticationRequest) throws Exception {
		LOGGER.log(Level.INFO,
				"******** " + authenticationRequest.getUsername() + " " + authenticationRequest.getPassword());
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		UserDetails userDetails = jwtUserDetailsServiceImpl.loadUserByUsername(authenticationRequest.getUsername());
		User user = getByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		user.setToken(token);
		
		return user;
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
	public Resource loadProfileImageAsResource(long id) {
		User user = get(id);
		String imagePath = user.getImagePath();
		try {
			Path file = Paths.get(imagePath);
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException("Could not read file: profile_picture_" + id);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: profile_picture_" + id, e);
		}
	}
	
	@Override
	public void registerUser(User user) throws NoSuchAlgorithmException {
		String username = user.getUsername();
		String mail = user.getMail();
		
		if (userRepository.existsByUsername(username)) {
			throw new ValidationException("That username is already taken");
		}
		if (userRepository.findByMail(mail) != null) {
			throw new ValidationException("That email is already taken");
		}
		
		String name = user.getName();
		String lastName = user.getLastName();
		String password = user.getPassword();
		String encodedPassword = new BCryptPasswordEncoder().encode(password);
		Date birthday = user.getBirthday();
		
		userRepository.save(new User(username, name, lastName, mail, encodedPassword, birthday));
	}
	
	@Override
	public void profileImage(MultipartFile file, long id) throws Exception {
		String imageOriginalName = file.getOriginalFilename();
		String imageExtension = imageOriginalName.substring(imageOriginalName.lastIndexOf(".") + 1);
		String imageName = "profile_picture_" + id + "." + imageExtension;
		String imagePath = Paths.get(profileImagesDirectory, imageName).toString();
		long size = file.getSize();

		if (size > 5000000) {
			throw new Exception("The size of the image is to big");
		}
		
		// Save the file locally
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(imagePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			stream.write(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Optional<User> currentUser = userRepository.findById(id);
		
		if (!currentUser.isPresent()) {
			throw new Exception("User not found");
		}
		
		User userToUpdate = currentUser.get();
		
		userToUpdate.setImageName(imageName);
		userToUpdate.setImagePath(imagePath);
		userRepository.save(userToUpdate);
	}
	
	@Override
	public User updateUser(User user, long id) throws Exception {
		String username = user.getUsername();
		String name = user.getName();
		String lastName = user.getLastName();
		Date birthday = user.getBirthday();
		
		Optional<User> currentUser = userRepository.findById(id);
		
		if(!currentUser.isPresent()){
			throw new Exception("User not found");
		} 
		
		User userToUpdate = currentUser.get();
		
		if (userRepository.existsByUsername(username)) {
			if (!userToUpdate.getUsername().equals(username)) {
				throw new CustomError("That username is already taken");
			} 
		}
		
		userToUpdate.setName(name);
		userToUpdate.setLastName(lastName);
		userToUpdate.setUsername(username);
		userToUpdate.setBirthday(birthday);
		userRepository.save(userToUpdate);
		
		UserDetails userDetails = jwtUserDetailsServiceImpl.loadUserByUsername(username);
		final String token = jwtTokenUtil.generateToken(userDetails);
				
		userToUpdate.setToken(token);
		
		return userToUpdate;
	}
	
	@Override
	public User updateUserPassword(Password password, long id) throws Exception {
		String newPassword = password.getNewPassword();
		String oldPassword = password.getOldPassword();
		
		Optional<User> currentUser = userRepository.findById(id);
		
		if (!currentUser.isPresent()) {
			throw new Exception("User not found");
		} 
		User userToUpdate = currentUser.get();
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
		if (!encoder.matches(oldPassword, userToUpdate.getPassword())) {
			throw new UnauthorizedError("The passwords don't match");
		}
		
		String encodedPassword = new BCryptPasswordEncoder().encode(newPassword);
		userToUpdate.setPassword(encodedPassword);
		userRepository.save(userToUpdate);
		
		UserDetails userDetails = jwtUserDetailsServiceImpl.loadUserByUsername(userToUpdate.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
				
		userToUpdate.setToken(token);
		
		return userToUpdate;
	}

	@Override
	public void delete(long id) throws Exception {
		Optional<User> currentUser = userRepository.findById(id);
		
		if (!currentUser.isPresent()) {
			throw new Exception("User not found");
		} 
		
		User userToDelete = currentUser.get();
		userRepository.deleteById(userToDelete.getId());

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
	public void resetPassword(String userMail) throws Exception {
		
		if (userRepository.findByMail(userMail) == null) {
			throw new Exception("No user found with that Email");
		}
		
		User user = userRepository.findByMail(userMail);
		
		String newPassword = randomPassword(3);
		String encodedPassword = new BCryptPasswordEncoder().encode(newPassword);
		user.setPassword(encodedPassword);
		userRepository.save(user);
		
		SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
		passwordResetEmail.setFrom("thegiftcher@gmail.com");
		passwordResetEmail.setTo(user.getMail());
		passwordResetEmail.setSubject("Password Reset Request");
		passwordResetEmail.setText("Hi " + user.getName() + ",\nYou recently requested to reset your password for The Giftcher App account.\nYour new password is "
		+ newPassword + "\nFor your account security we recommend you to change your password inmediatly after login. \n\nBest regards,\nThe Giftcher Team.");
		emailService.sendEmail(passwordResetEmail);
	}
	
	public static String randomPassword(int len) {
	    String A = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    String B = "abcdefghijklmnopqrstuvwxyz";
	    String C = "0123456789";
	    String D = "@!#$%";
	    
	    Random rnd = new Random();

	    StringBuilder aa = new StringBuilder(len);
	    StringBuilder bb = new StringBuilder(len);
	    StringBuilder cc = new StringBuilder(len);
	    StringBuilder dd = new StringBuilder(len);
	    for (int i = 0; i < len; i++) {
	        aa.append(A.charAt(rnd.nextInt(A.length())));
	        aa.append(B.charAt(rnd.nextInt(B.length())));
	        aa.append(C.charAt(rnd.nextInt(C.length())));
	        aa.append(D.charAt(rnd.nextInt(D.length())));
	    }
	    
	    String resetPassword = aa.toString() + bb.toString() + cc.toString() + dd.toString(); 
	    
	    return resetPassword;
	}

	@Override
	public User profileImageGoogleCloud(MultipartFile file, long id) throws Exception {
        checkFileExtension(file.getName());
        Optional<User> currentUser = userRepository.findById(id);
        User user = currentUser.get();
        
    	String imageOriginalName = file.getOriginalFilename();
		String imageExtension = imageOriginalName.substring(imageOriginalName.lastIndexOf(".") + 1);
		String imageName = UUID.randomUUID().toString() + "." + imageExtension;
		
		File image = convertMultiPartToFile(file);
        InputStream inputStream = new FileInputStream(image);
    
        Bucket bucket = getBucket("thegiftcher");
        Blob blob = bucket.create(imageName, inputStream, file.getContentType());
        System.out.println(blob.getSelfLink());
        
        String imageLink = "https://storage.googleapis.com/thegiftcher/" + imageName;
        user.setImagePath(imageLink);
        user.setImageName(imageName);
        userRepository.save(user);
        
        UserDetails userDetails = jwtUserDetailsServiceImpl.loadUserByUsername(user.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
				
		user.setToken(token);
        
        return user;
    }
	
	private Bucket getBucket(String bucketName) throws IOException {
		Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("/home/ec2-user/GCP.json"));
	    Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
	    Bucket bucket = storage.get(bucketName);
	    if (bucket == null) {
	      throw new IOException("Bucket not found:"+bucketName);
	    }
	    return bucket;
	  }
	
	private File convertMultiPartToFile(MultipartFile file ) throws IOException
    {
        File convFile = new File( file.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }

    private void checkFileExtension(String fileName) throws ServletException {
        if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
            String[] allowedExt = {".jpg", ".jpeg", ".png", ".gif"};
            for (String ext : allowedExt) {
                if (fileName.endsWith(ext)) {
                    return;
                }
            }
            throw new ServletException("file must be an image");
        }
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
