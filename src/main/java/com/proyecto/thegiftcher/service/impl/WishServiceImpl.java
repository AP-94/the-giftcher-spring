package com.proyecto.thegiftcher.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.service.IUserService;
import com.proyecto.thegiftcher.service.IWishService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.thegiftcher.domain.Wish;
import com.proyecto.thegiftcher.repository.WishRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Service
public class WishServiceImpl implements IWishService {

	private final WishRepository wishRepository;
	private final IUserService userService;
	public static String wishImagesDirectory = "/home/ec2-user/wishImages";


	public WishServiceImpl(WishRepository wishRepository, IUserService userService) {
		this.wishRepository = wishRepository;
		this.userService = userService;
	}
	
	@Override
	public List<Wish> getAll() {
		return (List<Wish>) wishRepository.findAll();
	}

	@Override
	public List<Wish> getAll(HttpServletRequest request) {
		User user = userService.getUserLogged(request);

		return (List<Wish>) wishRepository.findAllWishesByUserId(user.getId());
	}

	@Override
	public List<Wish> getUserWishes(long userId) {
		User user = userService.get(userId);

		return (List<Wish>) wishRepository.findAllWishesByUserId(user.getId());
	}

	@Override
	public Wish get(long id, HttpServletRequest request) throws Exception {
		User user = userService.getUserLogged(request);
		Optional<Wish> wish = wishRepository.findWishByUserIdAndId(user.getId(), id);
		if (wish.isPresent()) {
			return wish.get();
		} else {
			throw new Exception("Wish not found");
		}
	}

	@Override
	public List<Wish> getWishByCategoryId(long categoryId){
		return (List<Wish>) wishRepository.findWishesByCategoryId(categoryId);
	}

	@Override
	public Wish create(Wish wish, HttpServletRequest request) {
		User user = userService.getUserLogged(request);
		wish.setUserId(user.getId());
		Wish save = wishRepository.save(wish);
		return save;
	}


	@Override
	public Wish copyWishFromUser(long userId, long id, HttpServletRequest request) throws Exception {
		//recuperar si existe
		Optional<Wish> wishByUserIdAndId = wishRepository.findWishByUserIdAndId(userId, id);
		if (wishByUserIdAndId.isPresent()) {
			Wish wish = wishByUserIdAndId.get();
			User user = userService.getUserLogged(request);
			Wish newWish = new Wish();
			//copiar
			newWish.setUserId(user.getId());
			newWish.setName(wish.getName());
			newWish.setCategory(wish.getCategory());
			newWish.setDate(wish.getDate());
			newWish.setDescription(wish.getDescription());
			newWish.setImagePath(wish.getImagePath());
			newWish.setImageName(wish.getImageName());
			newWish.setLocation(wish.getLocation());
			newWish.setOnline_shop(wish.getOnlineShop());
			newWish.setReserved(wish.isReserved());
			newWish.setPrice(wish.getPrice());
			newWish.setShop(wish.getShop());
			newWish.setImageName(wish.getImageName());

			//insertar
			wishRepository.save(newWish);
			return newWish;
		} else {
			throw new Exception("Wish not found");
		}
	}

	@Override
	public Wish modify(Wish wish, long id, HttpServletRequest request) throws Exception {
		User user = userService.getUserLogged(request);
		
		if (wishRepository.findWishByUserIdAndId(user.getId(), id).isPresent()) {
			Optional<Wish> getWish = wishRepository.findWishByUserIdAndId(user.getId(), id);
			Wish modifiedWish = getWish.get();
			modifiedWish.setName(wish.getName());
			modifiedWish.setPrice(wish.getPrice());
			modifiedWish.setDescription(wish.getDescription());
			modifiedWish.setCategory(wish.getCategory());
			modifiedWish.setLocation(wish.getLocation());
			modifiedWish.setOnline_shop(wish.getOnlineShop());
			modifiedWish.setShop(wish.getShop());
			modifiedWish.setImagePath(wish.getImagePath());
			modifiedWish.setImageName(wish.getImageName());
			modifiedWish.setReserved(wish.isReserved());
			modifiedWish.setImageName(wish.getImageName());
			wishRepository.save(modifiedWish);
			return modifiedWish;
		} else {
			throw new Exception("Wish not found");
		}
	}

	@Override
	public void delete(long id, HttpServletRequest request) {
		User user = userService.getUserLogged(request);
		wishRepository.deleteByUserIdAndId(user.getId(), id);
	}
	
	@Override
	public void addImages(long id, MultipartFile file, HttpServletRequest request ) throws Exception{
		User user = userService.getUserLogged(request);
		String imageOriginalName = file.getOriginalFilename();
		String imageExtension = imageOriginalName.substring(imageOriginalName.lastIndexOf(".") + 1);
		String imageName = "wish_image_" + id + "." + imageExtension;
		String imagePath = Paths.get(wishImagesDirectory, imageName).toString();
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
		
		Optional<Wish> currentWish = wishRepository.findWishByUserIdAndId(user.getId(), id);
		
		if (!currentWish.isPresent()) {
			throw new Exception("Wish not found");
		}
		
		Wish wishToUpdate = currentWish.get();
		

		wishToUpdate.setImageName(imageName);
		wishToUpdate.setImagePath(imagePath);
		wishRepository.save(wishToUpdate);
	}

	@Override
	public Wish getById(long id) {
		return wishRepository.getOne(id);
	}

	@Override
	public Wish wishImageGoogleCloud(MultipartFile file, long id, HttpServletRequest request) throws Exception {
        checkFileExtension(file.getName());
        User user = userService.getUserLogged(request);
        
    	String imageOriginalName = file.getOriginalFilename();
		String imageExtension = imageOriginalName.substring(imageOriginalName.lastIndexOf(".") + 1);
		String imageName = UUID.randomUUID().toString() + "." + imageExtension;
		
		File image = convertMultiPartToFile(file);
        InputStream inputStream = new FileInputStream(image);
    
        Bucket bucket = getBucket("thegiftcher");
        Blob blob = bucket.create(imageName, inputStream, file.getContentType());
        System.out.println(blob.getSelfLink());
        
        
        Optional<Wish> currentWish = wishRepository.findWishByUserIdAndId(user.getId(), id);
		
		if (!currentWish.isPresent()) {
			throw new Exception("Wish not found");
		}
		
        Wish wishToUpdate = currentWish.get();
        
        String imageLink = "https://storage.googleapis.com/thegiftcher/" + imageName;
        wishToUpdate.setImagePath(imageLink);
        wishToUpdate.setImageName(imageName);
        
        return wishRepository.save(wishToUpdate);
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

}
