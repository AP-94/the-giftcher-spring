package com.proyecto.thegiftcher.service.impl;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.proyecto.thegiftcher.domain.ReservedWishes;
import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.domain.Wish;
import com.proyecto.thegiftcher.repository.ReservedWishesRepository;
import com.proyecto.thegiftcher.repository.WishRepository;
import com.proyecto.thegiftcher.service.IReservedWishesService;
import com.proyecto.thegiftcher.service.IUserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ReservedWishesServiceImpl implements IReservedWishesService {

    private final ReservedWishesRepository reservedWishesRepository;
    private final IUserService userService;
    private final WishRepository wishRepository;

    public ReservedWishesServiceImpl(ReservedWishesRepository reservedWishesRepository, IUserService userService, WishRepository wishRepository) {
        this.reservedWishesRepository = reservedWishesRepository;
        this.userService = userService;
        this.wishRepository = wishRepository;
    }

    @Override
    public List<ReservedWishes> getAll(HttpServletRequest request) {
        User user = userService.getUserLogged(request);
        return reservedWishesRepository.findAllByUserId(user.getId());
    }

    @Override
    public ReservedWishes create(ReservedWishes reservedWish, HttpServletRequest request) {
        User user = userService.getUserLogged(request);

        try {
            User friend = userService.get(reservedWish.getFriendId());
        } catch (Exception e) {
            throw new UserNotFoundException("Friend not found");
        }


        Wish wish = wishRepository.getOne(reservedWish.getWishId());
        wish.setReserved(Boolean.TRUE);
        wishRepository.save(wish);

        reservedWish.setUserId(user.getId());
        return reservedWishesRepository.save(reservedWish);
    }

    @Override
    public void delete(long id) {
        ReservedWishes reservedWishes = reservedWishesRepository.getOne(id);

        Wish wish = wishRepository.getOne(reservedWishes.getWishId());
        wish.setReserved(Boolean.FALSE);
        wishRepository.save(wish);

        reservedWishesRepository.delete(reservedWishes);
    }
}
