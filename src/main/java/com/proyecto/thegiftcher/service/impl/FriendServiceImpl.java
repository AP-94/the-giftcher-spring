package com.proyecto.thegiftcher.service.impl;

import com.proyecto.thegiftcher.domain.Friend;
import com.proyecto.thegiftcher.domain.FriendRequest;
import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.repository.FriendRepository;
import com.proyecto.thegiftcher.repository.FriendRequestRepository;
import com.proyecto.thegiftcher.service.IFriendService;
import com.proyecto.thegiftcher.service.IUserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class FriendServiceImpl implements IFriendService {

	private final FriendRequestRepository friendRequestRepository;
	private final FriendRepository friendRepository;
	private final IUserService userService;


	public FriendServiceImpl(FriendRequestRepository friendRequestRepository, FriendRepository friendRepository, IUserService userService) {
		this.friendRequestRepository = friendRequestRepository;
		this.friendRepository = friendRepository;
		this.userService = userService;
	}

	@Override
	public List<Friend> getFriends(HttpServletRequest request) {
		User user = userService.getUserLogged(request);
		return (List<Friend>) friendRepository.findAllFriendsByUserId(user.getId());
	}

	@Override
	public List<FriendRequest> getRequestFriends(HttpServletRequest request) {
		User user = userService.getUserLogged(request);

		return (List<FriendRequest>) friendRequestRepository.findAllFriendsRequestsByFriendRequestId(user.getId());
	}

	@Override
	public void createFriendRequest(FriendRequest friendRequest, HttpServletRequest request) {
		User user = userService.getUserLogged(request);
		friendRequest.setUserId(user.getId());
		friendRequestRepository.save(friendRequest);
	}


	@Override
	public void confirmFriendRequest(long id, HttpServletRequest request) {

		//recuperar solicitud
		final FriendRequest friendRequest = friendRequestRepository.getOne(id);

		//Insertar en nueva tabla, para cada user
		Friend friend = new Friend();
		friend.setUserId(friendRequest.getFriendRequestId());
		friend.setFriendId(friendRequest.getUserId());
		friendRepository.save(friend);
		friend = new Friend();
		friend.setUserId(friendRequest.getUserId());
		friend.setFriendId(friendRequest.getFriendRequestId());
		friendRepository.save(friend);

		//borrar solicitud
		friendRequestRepository.deleteById(id);
	}

	@Override
	public void deleteFriendRequest(long id) {
		friendRequestRepository.deleteById(id);
	}

	@Override
	public void deleteFriend(long id) {
		final Friend friend = friendRepository.getOne(id);

		Optional<Friend> userIdAndFriendId = friendRepository.findByUserIdAndFriendId(friend.getUserId(), friend.getFriendId());
		userIdAndFriendId.ifPresent(value -> friendRepository.deleteById(value.getId()));

		userIdAndFriendId = friendRepository.findByUserIdAndFriendId(friend.getFriendId(), friend.getUserId());
		userIdAndFriendId.ifPresent(value -> friendRepository.deleteById(value.getId()));
	}
}
