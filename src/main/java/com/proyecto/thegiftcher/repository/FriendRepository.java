package com.proyecto.thegiftcher.repository;

import com.proyecto.thegiftcher.domain.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FriendRepository extends JpaRepository<FriendRequest, Long> {
    Iterable<FriendRequest> findAllFriendsRequestsByFriendRequestId(long friendRequestId);

    @Transactional
    @Modifying
    void deleteByUserIdAndId(long userId, long id);
}