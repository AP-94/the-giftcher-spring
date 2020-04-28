package com.proyecto.thegiftcher.service;

import com.proyecto.thegiftcher.domain.Friend;
import com.proyecto.thegiftcher.domain.FriendRequest;
import com.proyecto.thegiftcher.web.BadRequestAlertException;
import com.proyecto.thegiftcher.web.FriendException;
import com.proyecto.thegiftcher.web.FriendRequestException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IFriendService {

	List<Friend> getFriends(HttpServletRequest request);
	List<FriendRequest> getRequestFriends(HttpServletRequest userId);
	void createFriendRequest(FriendRequest friendRequest, HttpServletRequest request) throws BadRequestAlertException, FriendRequestException, FriendException;
	void confirmFriendRequest(long id, HttpServletRequest request);
	void deleteFriendRequest(long id);
	void deleteFriend(long id);
}
