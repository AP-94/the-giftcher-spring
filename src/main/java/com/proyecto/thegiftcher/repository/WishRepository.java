package com.proyecto.thegiftcher.repository;

import com.proyecto.thegiftcher.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    Iterable<Wish> findAllWishesByUserId(long userId);
    Optional<Wish> findWishByUserIdAndId(long userId, long id);
    Iterable<Wish> findWishesByCategoryId(long categoryId);

    @Transactional
    @Modifying
    void deleteByUserIdAndId(long userId, long id);
}