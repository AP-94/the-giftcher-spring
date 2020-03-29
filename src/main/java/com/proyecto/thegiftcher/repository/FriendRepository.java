package com.proyecto.thegiftcher.repository;

import com.proyecto.thegiftcher.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    Iterable<Friend> findAllFriendsByUserId(long userId);
}