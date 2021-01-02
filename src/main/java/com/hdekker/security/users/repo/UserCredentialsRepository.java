package com.hdekker.security.users.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hdekker.security.users.data.UserCredentials;

@Repository
public interface UserCredentialsRepository extends CrudRepository<UserCredentials, Integer>{

	UserCredentials findFirstByUserName(String name);

}
