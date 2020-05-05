package com.proyecto.thegiftcher.web.controller;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.thegiftcher.domain.*;
import com.proyecto.thegiftcher.service.IReservedWishesService;
import com.proyecto.thegiftcher.service.IUserService;
import com.proyecto.thegiftcher.service.IWishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class ReservedWishesController {

    private final IReservedWishesService iReservedWishesService;
    private final IWishService iWishService;
    private final IUserService iUserService;

    public ReservedWishesController(IReservedWishesService iReservedWishesService, IWishService iWishService, IUserService iUserService) {
        this.iReservedWishesService = iReservedWishesService;
        this.iWishService = iWishService;
        this.iUserService = iUserService;
    }

    @GetMapping(path = "/reserved_wishes")
    public ResponseEntity<ReservedWishesResponseDTO> getReservedWishes(HttpServletRequest request) {
        ReservedWishesResponseDTO reservedWishesResponseDTO = new ReservedWishesResponseDTO();

        List<ReservedWishes> reservedWishes = iReservedWishesService.getAll(request);
        try {
            System.out.println(new ObjectMapper().writeValueAsString(reservedWishes));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        if (CollectionUtils.isEmpty(reservedWishes)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<Long, ReservedWishesDTO> listMap = new HashMap<>();
        for (ReservedWishes reservedWish : reservedWishes) {
            if (!listMap.containsKey(reservedWish.getFriendId())) {
                ReservedWishesDTO reservedWishesDTO = new ReservedWishesDTO();

                User friend;
                try {
                    friend = iUserService.get(reservedWish.getFriendId());
                } catch (Exception e) {
                    throw new UserNotFoundException("Friend not found");
                }
                reservedWishesDTO.setFriendId(reservedWish.getFriendId());
                reservedWishesDTO.setFriendName(friend.getName());

                reservedWishesDTO.setFriendReservedWishes(new ArrayList<>());
                Wish wish = iWishService.getById(reservedWish.getWishId() );

                reservedWishesDTO.getFriendReservedWishes().add(mapperWish(reservedWish.getId(), wish));
                listMap.put(reservedWish.getFriendId(), reservedWishesDTO);
            } else {
                ReservedWishesDTO reservedWishesDTO = listMap.get(reservedWish.getFriendId());

                Wish wish = iWishService.getById(reservedWish.getWishId());
                reservedWishesDTO.getFriendReservedWishes().add(mapperWish(reservedWish.getId(), wish));
                listMap.put(reservedWish.getFriendId(), reservedWishesDTO);
            }
        }
        Collection<ReservedWishesDTO> values = listMap.values();
        reservedWishesResponseDTO.setReservedWishes(new ArrayList<>(values));

        return new ResponseEntity<>(reservedWishesResponseDTO, HttpStatus.OK);
    }

    private WishDTO mapperWish(Long id, Wish wish) {
        WishDTO wishDTO = new WishDTO();
        wishDTO.setId(id);
        wishDTO.setWishId(wish.getId());
        wishDTO.setCategoryId(wish.getCategory());
        wishDTO.setDescription(wish.getDescription());
        wishDTO.setName(wish.getName());
        wishDTO.setImageName(wish.getImageName());
        wishDTO.setImagePath(wish.getImagePath());
        wishDTO.setPrice(wish.getPrice());
        wishDTO.setReserved(wish.isReserved());
        return wishDTO;
    }

    @PostMapping(path = "/reserved_wishes")
    public ReservedWishes reserveWish(@RequestBody ReservedWishes reservedWish, HttpServletRequest request) {
        return iReservedWishesService.create(reservedWish, request);
    }

    @DeleteMapping(path = "/reserved_wishes/{id}")
    public  Map<String, String> reserveWish(@PathVariable long id) {

        iReservedWishesService.delete(id);
        return Collections.singletonMap("message", "true");
    }
}
