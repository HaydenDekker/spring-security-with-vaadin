package com.hdekker.security.routes;

import java.util.Optional;

import com.hdekker.security.services.data.User;
import com.vaadin.flow.spring.annotation.UIScope;

/**
 *  Holds the state of the User Mangement User flow per UI.
 *  
 *  User Management user  flow has 
 *  	- list
 *  	- add
 *  	- edit
 * 
 */
@UIScope
public class UserFlowState {

	Optional<User> user = Optional.empty();

	public Optional<User> getUser() {
		return user;
	}

	public void setUser(Optional<User> user) {
		this.user = user;
	}
	
	
	
}
