package com.hdekker.security.configuration;

public interface SecurityBaseRoles {

	/**
	 * Must explicitly declare a route as public otherwise route is secured
	 * By default Vaadin routes are allowed if user is logged in.
	 * 
	 */
	public static final String PUBLIC = "PUBLIC";
	
	/**
	 * The ADMIN user has access the to User Management View that bolts
	 * onto the application. Must always have at least one user.
	 * 
	 */
	public static final String ADMIN = "ADMIN";
	
	
}
