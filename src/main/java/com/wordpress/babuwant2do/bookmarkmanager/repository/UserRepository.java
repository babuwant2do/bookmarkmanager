package com.wordpress.babuwant2do.bookmarkmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wordpress.babuwant2do.bookmarkmanager.domain.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findOneByLogin(String login);
	Optional<User> findOneByEmail(String email);
}
