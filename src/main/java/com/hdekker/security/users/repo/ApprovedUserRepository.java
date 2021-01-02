package com.hdekker.security.users.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hdekker.security.users.data.ApprovedUser;

@Repository
public interface ApprovedUserRepository extends CrudRepository<ApprovedUser, Integer> {

	ApprovedUser findFirstByUserCredentialsUserName(String userName);

}
