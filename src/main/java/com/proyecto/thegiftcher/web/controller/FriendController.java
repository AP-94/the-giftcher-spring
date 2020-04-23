package com.proyecto.thegiftcher.web.controller;

import com.proyecto.thegiftcher.domain.FriendRequest;
import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.service.IFriendService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class FriendController {

	private final IFriendService friendService;

	public FriendController(IFriendService friendService) {
		this.friendService = friendService;
	}

	@GetMapping(path = "/friends")
	public ResponseEntity<List<User>> getFriends(HttpServletRequest request) {
		List<User> friends = friendService.getFriends(request);
		if (CollectionUtils.isEmpty(friends)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(friends, HttpStatus.OK);
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
		friendService.createFriendRequest(friendRequest, request);

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
