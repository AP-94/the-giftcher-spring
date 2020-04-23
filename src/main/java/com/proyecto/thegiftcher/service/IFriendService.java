package com.proyecto.thegiftcher.service;

import com.proyecto.thegiftcher.domain.FriendRequest;
import com.proyecto.thegiftcher.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IFriendService {

	List<User> getFriends(HttpServletRequest request);
	List<FriendRequest> getRequestFriends(HttpServletRequest userId);
	void createFriendRequest(FriendRequest friendRequest, HttpServletRequest request);
	void confirmFriendRequest(long id, HttpServletRequest request);
	void deleteFriendRequest(long id);
	void deleteFriend(long id);
}
