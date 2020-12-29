package com.hdekker.security.users.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hdekker.security.users.data.UserRequests;

@Repository
public interface UserRequestRepository extends JpaRepository<UserRequests, Integer>{

}
