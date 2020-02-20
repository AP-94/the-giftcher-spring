package com.proyecto.thegiftcher.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.thegiftcher.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	

}
