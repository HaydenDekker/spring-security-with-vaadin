package com.hdekker.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hdekker.security.users.data.ApprovedUser;
import com.hdekker.security.users.repo.ApprovedUserRepository;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

@Service
@VaadinSessionScope
public class VaadinApplicationSecurityContext {

	Logger log = LoggerFactory.getLogger(VaadinApplicationSecurityContext.class);
	
	@Autowired
	ApprovedUserRepository approvedUserRepository;
	
	public ApprovedUser getApprovedUser() {
		
		ApprovedUser userProfile = null;
		
		if(SecurityConfiguration.securityEnabled) {
			
			userProfile = (ApprovedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			log.info("A user session for " + userProfile.getUser().getEmail() + " has just been created.");
			
		}else {
			
			userProfile = approvedUserRepository.findAll().get(0);
			log.info("A user session for " + userProfile.getUser().getEmail() + " has just been created.");
			
		}
		
		return userProfile;
		
	}
}
