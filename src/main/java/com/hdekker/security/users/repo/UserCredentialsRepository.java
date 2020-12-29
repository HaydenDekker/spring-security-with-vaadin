package com.hdekker.security.users.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hdekker.security.users.data.UserCredentials;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Integer>{

	UserCredentials findFirstByUserName(String name);

}
