package com.proyecto.thegiftcher.service.Impl;

import com.proyecto.thegiftcher.domain.FriendRequest;
import com.proyecto.thegiftcher.domain.User;
import com.proyecto.thegiftcher.repository.FriendRepository;
import com.proyecto.thegiftcher.service.IFriendService;
import com.proyecto.thegiftcher.service.IUserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class FriendServiceImpl implements IFriendService {

	private final FriendRepository friendRepository;
	private final IUserService userService;


	public FriendServiceImpl(FriendRepository friendRepository, IUserService userService) {
		this.friendRepository = friendRepository;
		this.userService = userService;
	}

	@Override
	public List<FriendRequest> getRequestFriends(HttpServletRequest request) {
		User user = userService.getUserLogged(request);

		return (List<FriendRequest>) friendRepository.findAllFriendsRequestsByFriendRequestId(user.getId());
	}

	@Override
	public void createFriendRequest(FriendRequest friendRequest, HttpServletRequest request) {
		User user = userService.getUserLogged(request);
		friendRequest.setUserId(user.getId());
		friendRepository.save(friendRequest);
	}


	@Override
	public void confirmFriendRequest(long id, HttpServletRequest request) {
		User user = userService.getUserLogged(request);

		
	}

	@Override
	public void deleteFriendRequest(long id, HttpServletRequest request) {
		User user = userService.getUserLogged(request);
		friendRepository.deleteByUserIdAndId(user.getId(), id);
	}

}
