package com.proyecto.thegiftcher.service;

import com.proyecto.thegiftcher.domain.FriendRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IFriendService {

	List<FriendRequest> getRequestFriends(HttpServletRequest userId);
	void createFriendRequest(FriendRequest friendRequest, HttpServletRequest request);
	void confirmFriendRequest(long id, HttpServletRequest request);
	void deleteFriendRequest(long id, HttpServletRequest request);
}
