package com.hdekker.security.users.data;


/**
 * A class to allow users to request access to the
 * site.
 * 
 * @author hdekker
 *
 */

public class UserRequests {

	Integer id;
	String email;
	String firstName;
	String secondName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	
}
