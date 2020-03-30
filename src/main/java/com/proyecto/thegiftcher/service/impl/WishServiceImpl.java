package com.proyecto.thegiftcher.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.service.IUserService;
import com.proyecto.thegiftcher.service.IWishService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.thegiftcher.domain.Wish;
import com.proyecto.thegiftcher.repository.WishRepository;

import javax.servlet.http.HttpServletRequest;

@Service
public class WishServiceImpl implements IWishService {

	private final WishRepository wishRepository;
	private final IUserService userService;
	public static String wishImagesDirectory = System.getProperty("user.dir") + "/wishImages";


	public WishServiceImpl(WishRepository wishRepository, IUserService userService) {
		this.wishRepository = wishRepository;
		this.userService = userService;
	}

	@Override
	public List<Wish> getAll(HttpServletRequest request) {
		User user = userService.getUserLogged(request);

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
	public void create(Wish wish, HttpServletRequest request) {
		User user = userService.getUserLogged(request);
		wish.setUserId(user.getId());
		wishRepository.save(wish);
	}


	@Override
	public void copyWishFromUser(long userId, long id, HttpServletRequest request) {
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
		}

	}

	@Override
	public void modify(Wish wish, long id, HttpServletRequest request) {
		User user = userService.getUserLogged(request);
		wishRepository.findWishByUserIdAndId(user.getId(), id).ifPresent(x -> {
			x.setName(wish.getName());
			x.setPrice(wish.getPrice());
			x.setDescription(wish.getDescription());
			x.setCategory(wish.getCategory());
			x.setLocation(wish.getLocation());
			x.setOnline_shop(wish.getOnlineShop());
			x.setShop(wish.getShop());
			x.setImagePath(wish.getImagePath());
			x.setImageName(wish.getImageName());
			x.setReserved(wish.isReserved());
			x.setImageName(wish.getImageName());
			wishRepository.save(x);
		});
		
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

}
