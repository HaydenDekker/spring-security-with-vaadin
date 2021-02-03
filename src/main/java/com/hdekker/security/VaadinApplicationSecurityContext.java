package com.hdekker.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hdekker.security.configuration.SecurityConfiguration;
import com.hdekker.security.services.UserService;
import com.hdekker.security.services.data.User;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

/**
 * 
 * TODO what is this actually for?
 * @author HDekker
 *
 */
@Service
@VaadinSessionScope
public class VaadinApplicationSecurityContext {

	Logger log = LoggerFactory.getLogger(VaadinApplicationSecurityContext.class);
	
	@Autowired
	UserService userService;
	
	public User getApprovedUser() {
		
		User userProfile = null;
			
			//TODO reimplement a suitable log.
			userProfile = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			//log.info("A user session for " + userProfile.getUser().getEmail() + " has just been created.");
	
			//userProfile = approvedUserRepository.findAll().get(0);
			//log.info("A user session for " + userProfile.getUser().getEmail() + " has just been created.");
			
		return userProfile;
		
	}
}
