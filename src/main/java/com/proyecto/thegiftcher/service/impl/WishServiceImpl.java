package com.proyecto.thegiftcher.service.impl;

import java.util.List;
import java.util.Optional;

import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.service.IUserService;
import com.proyecto.thegiftcher.service.IWishService;
import org.springframework.stereotype.Service;

import com.proyecto.thegiftcher.domain.Wish;
import com.proyecto.thegiftcher.repository.WishRepository;

import javax.servlet.http.HttpServletRequest;

@Service
public class WishServiceImpl implements IWishService {

	private final WishRepository wishRepository;
	private final IUserService userService;


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
			newWish.setImagesPaths(wish.getImagesPaths());
			newWish.setImagesNames(wish.getImagesNames());
			newWish.setLocation(wish.getLocation());
			newWish.setOnline_shop(wish.getOnlineShop());
			newWish.setReserved(wish.isReserved());
			newWish.setPrice(wish.getPrice());
			newWish.setShop(wish.getShop());
			newWish.setImagesNames(wish.getImagesNames());

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
			x.setImagesPaths(wish.getImagesPaths());
			x.setImagesNames(wish.getImagesNames());
			x.setReserved(wish.isReserved());
			x.setImagesNames(wish.getImagesNames());
			wishRepository.save(x);
		});
		
	}

	@Override
	public void delete(long id, HttpServletRequest request) {
		User user = userService.getUserLogged(request);
		wishRepository.deleteByUserIdAndId(user.getId(), id);
	}

}
