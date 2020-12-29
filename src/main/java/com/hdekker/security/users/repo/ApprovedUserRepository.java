package com.hdekker.security.users.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hdekker.security.users.data.ApprovedUser;

@Repository
public interface ApprovedUserRepository extends JpaRepository<ApprovedUser, Integer> {

	ApprovedUser findFirstByUserCredentialsUserName(String userName);

}
