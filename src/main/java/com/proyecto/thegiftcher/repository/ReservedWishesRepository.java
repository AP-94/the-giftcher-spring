package com.proyecto.thegiftcher.repository;

import com.proyecto.thegiftcher.domain.ReservedWishes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservedWishesRepository extends JpaRepository<ReservedWishes, Long> {
    List<ReservedWishes> findAllByUserId(Long userId);
}
