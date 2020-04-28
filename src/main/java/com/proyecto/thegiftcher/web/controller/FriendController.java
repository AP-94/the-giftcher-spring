package com.proyecto.thegiftcher.web.controller;

import com.proyecto.thegiftcher.domain.*;
import com.proyecto.thegiftcher.service.IFriendService;
import com.proyecto.thegiftcher.service.IUserService;
import com.proyecto.thegiftcher.web.BadRequestAlertException;
import com.proyecto.thegiftcher.web.FriendException;
import com.proyecto.thegiftcher.web.FriendRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class FriendController {

    private final IFriendService friendService;
    private final IUserService userService;


    public FriendController(IFriendService friendService, IUserService userService) {
        this.friendService = friendService;
        this.userService = userService;
    }

    @GetMapping(path = "/friends")
    public ResponseEntity<FriendsResponseDTO> getFriends(HttpServletRequest request) {
        FriendsResponseDTO friendsResponseDTO = new FriendsResponseDTO();

        List<Friend> friends = friendService.getFriends(request);
        if (CollectionUtils.isEmpty(friends)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<FriendDTO> friendDTOS = new ArrayList<>();
        for (Friend friend : friends) {
            Long friendId = friend.getFriendId();
            User userFriend = userService.get(friendId);

            FriendDTO friendDTO = new FriendDTO();
            friendDTO.setId(friend.getId());
            friendDTO.setFriendId(friendId);
            friendDTO.setUsername(userFriend.getUsername());
            friendDTO.setName(userFriend.getName());
            friendDTO.setLastName(userFriend.getLastName());
            friendDTO.setMail(userFriend.getMail());
            friendDTO.setBirthday(userFriend.getBirthday());
            friendDTO.setImageName(userFriend.getImageName());
            friendDTO.setImagePath(userFriend.getImagePath());
            friendDTOS.add(friendDTO);
        }

        friendsResponseDTO.setFriends(friendDTOS);

        return new ResponseEntity<>(friendsResponseDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/friends/requests")
    public ResponseEntity<List<FriendRequest>> getRequestFriends(HttpServletRequest request) {
        List<FriendRequest> friendRequests = friendService.getRequestFriends(request);
        if (CollectionUtils.isEmpty(friendRequests)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(friendRequests, HttpStatus.OK);
    }

    @PostMapping(path = "/friends")
    public Map<String, String> createFriendRequest(@RequestBody FriendRequest friendRequest, HttpServletRequest request) {

        try {
            friendService.createFriendRequest(friendRequest, request);
        } catch (BadRequestAlertException e) {
            return Collections.singletonMap("message", "ERROR: You can't be your own friend");
        } catch (FriendRequestException e) {
            return Collections.singletonMap("message", "ERROR: This friend request already exists");
        } catch (FriendException e) {
            return  Collections.singletonMap("message", "ERROR: You are already friends");
        }

        return Collections.singletonMap("message", "true");
    }

    @PutMapping(path = "/friends/{id}")
    public Map<String, String> confirmFriendRequest(@PathVariable long id, HttpServletRequest request) {
        friendService.confirmFriendRequest(id, request);

        return Collections.singletonMap("message", "true");
    }

    @DeleteMapping(path = "/friends/requests/{id}")
    public Map<String, String> deleteFriendRequest(@PathVariable long id) {
        friendService.deleteFriendRequest(id);

        return Collections.singletonMap("message", "true");
    }

    @DeleteMapping(path = "/friends/{id}")
    public Map<String, String> deleteFriend(@PathVariable long id) {
        friendService.deleteFriend(id);

        return Collections.singletonMap("message", "true");
    }

}
