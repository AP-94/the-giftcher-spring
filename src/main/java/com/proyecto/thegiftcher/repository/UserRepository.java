package com.proyecto.thegiftcher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.thegiftcher.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByMail(String mail);
	public Boolean existsByUsername(String username);
	User findByUsername(String username);

}
