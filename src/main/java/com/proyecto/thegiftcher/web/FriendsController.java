package com.proyecto.thegiftcher.web;

import com.proyecto.thegiftcher.domain.FriendRequest;
import com.proyecto.thegiftcher.service.IFriendService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class FriendsController {

	private final IFriendService friendService;

	public FriendsController(IFriendService friendService) {
		this.friendService = friendService;
	}
	
	@GetMapping(path = "/friends")
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
	public void confirmFriendRequest(@RequestBody FriendRequest friendRequest, @PathVariable long id, HttpServletRequest request) {
		friendService.confirmFriendRequest(id, request);
	}

	@DeleteMapping(path = "/friends/{id}")
	public void deleteFriendRequest(@PathVariable long id, HttpServletRequest request) {
		friendService.deleteFriendRequest(id, request);
	}
   
}

