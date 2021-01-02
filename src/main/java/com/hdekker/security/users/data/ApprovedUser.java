package com.hdekker.security.users.data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ApprovedUser {

	Integer id;
	User user;
	UserCredentials userCredentials;
	LocalDateTime approvedOn;
	LocalDateTime lastActiveOn;
	Boolean isActive;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserCredentials getUserCredentials() {
		return userCredentials;
	}
	public void setUserCredentials(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}
	public LocalDateTime getApprovedOn() {
		return approvedOn;
	}
	public void setApprovedOn(LocalDateTime approvedOn) {
		this.approvedOn = approvedOn;
	}
	public LocalDateTime getLastActiveOn() {
		return lastActiveOn;
	}
	public void setLastActiveOn(LocalDateTime lastActiveOn) {
		this.lastActiveOn = lastActiveOn;
	}
	
	
	
}
