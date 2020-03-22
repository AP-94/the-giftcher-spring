package com.proyecto.thegiftcher.web.controller;

import com.proyecto.thegiftcher.domain.Friend;
import com.proyecto.thegiftcher.domain.FriendRequest;
import com.proyecto.thegiftcher.service.IFriendService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class FriendController {

	private final IFriendService friendService;

	public FriendController(IFriendService friendService) {
		this.friendService = friendService;
	}

	@GetMapping(path = "/friends")
	public ResponseEntity<List<Friend>> getFriends(HttpServletRequest request){
		List<Friend> friends = friendService.getFriends(request);
		if (CollectionUtils.isEmpty(friends)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(friends, HttpStatus.OK);
	}

	@GetMapping(path = "/friends/requests")
	public ResponseEntity<List<FriendRequest>> getRequestFriends(HttpServletRequest request){
		List<FriendRequest> friendRequests = friendService.getRequestFriends(request);
		if (CollectionUtils.isEmpty(friendRequests)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(friendRequests, HttpStatus.OK);
	}

	@PostMapping(path = "/friends")
	public void createFriendRequest(@RequestBody FriendRequest friendRequest, HttpServletRequest request) {
		friendService.createFriendRequest(friendRequest, request);
	}

	@PutMapping(path = "/friends/{id}")
	public void confirmFriendRequest(@PathVariable long id, HttpServletRequest request) {
		friendService.confirmFriendRequest(id, request);
	}

	@DeleteMapping(path = "/friends/requests/{id}")
	public void deleteFriendRequest(@PathVariable long id) {
		friendService.deleteFriendRequest(id);
	}

	@DeleteMapping(path = "/friends/{id}")
	public void deleteFriend(@PathVariable long id) {
		friendService.deleteFriend(id);
	}
   
}

