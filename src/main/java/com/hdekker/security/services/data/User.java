package com.hdekker.security.services.data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TODO DynamoPartitionIssue - This item would change regularly due to the attribute lastActiveOn.
 * 
 * @author HDekker
 *
 */

public class User{

	String userName; 			// no-changed
	
	String firstName; 			// slow-change
	String secondName; 			// slow-change
	String email; 				// medium-change
	
	String passWord; 			// monthly-change
	
	List<String> roles; 		// Intermittent-change
	
	LocalDateTime createdOn; 	// no-change
	LocalDateTime lastModified; // highly-frequent-change
	
	Boolean isActive; 			// slow-change
	
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public LocalDateTime getLastModified() {
		return lastModified;
	}
	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}
 
}
