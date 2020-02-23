package com.proyecto.thegiftcher.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.thegiftcher.domain.Wish;

@Repository
public interface WishRepository extends CrudRepository<Wish, Long> {
	

}