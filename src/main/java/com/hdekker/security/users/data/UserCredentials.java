package com.hdekker.security.users.data;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserCredentials {

	Integer id;
	String userName;
	String passWord;
	List<String> credientials;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	@ElementCollection
	public List<String> getCredientials() {
		return credientials;
	}
	public void setCredientials(List<String> credientials) {
		this.credientials = credientials;
	}
	
	
	
}
