package com.hdekker.security.users.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hdekker.security.users.data.User;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	User findFirstByUserName(String string);

}
