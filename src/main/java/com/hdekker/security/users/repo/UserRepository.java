package com.hdekker.security.users.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hdekker.security.users.data.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findFirstByUserName(String string);

}
