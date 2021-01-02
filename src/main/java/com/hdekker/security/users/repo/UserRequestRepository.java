package com.hdekker.security.users.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hdekker.security.users.data.UserRequests;

@Repository
public interface UserRequestRepository extends CrudRepository<UserRequests, Integer>{

}
