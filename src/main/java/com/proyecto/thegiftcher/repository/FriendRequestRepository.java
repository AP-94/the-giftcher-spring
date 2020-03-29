package com.proyecto.thegiftcher.repository;

import com.proyecto.thegiftcher.domain.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    Iterable<FriendRequest> findAllFriendsRequestsByFriendRequestId(long friendRequestId);
}