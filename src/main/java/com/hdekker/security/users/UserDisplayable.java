package com.hdekker.security.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public interface UserDisplayable {

	public String getUserName();
	public String getFirstName();
	public String getSecondName();
	
}
