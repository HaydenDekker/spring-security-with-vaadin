package com.hdekker.security.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hdekker.security.configuration.ConfigureUIServiceInitListener;
import com.hdekker.security.services.data.DyanmoDBDAO;
import com.hdekker.security.services.data.User;

@Service
@DependsOn("passwordEncoder")
public class UserService {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	DyanmoDBDAO userRepository;
	
	/**
	 * Mutates users last active date.
	 * 
	 * @param user
	 * @return
	 */
	
	public  CompletableFuture<Optional<User>> createUser(String name, String surname, String email, String userName, String password, List<String> roles) {
		
		User user = new User();
		
		user.setIsActive(true);
		user.setFirstName(name);
		user.setSecondName(surname);
		user.setEmail(email);
		user.setUserName(email); 
		user.setPassWord(passwordEncoder.encode(password));
		user.setUserName(userName);
		user.setRoles(roles);
		user.setLastModified(LocalDateTime.now());
		user.setCreatedOn(LocalDateTime.now());
		
		return userRepository.save(user);
		
	}
	
	public CompletableFuture<Optional<User>> saveUser(User user) {
		
		user.setLastModified(LocalDateTime.now());
		return userRepository.save(user);
		
	}

	/**
	 * Never deletes data, just sets the user as inactive.
	 * 
	 * @param user
	 */
	public void deleteUser(User user) {
		
		user.setIsActive(false);
		userRepository.delete(user.getUserName());
		
	}

	public CompletableFuture<Optional<User>> findUserByUserName(String name) {
		
			CompletableFuture<Optional<User>> user = userRepository.findFirstByUserName(name);
			return user;
		
	}
	
	/**
	 * Warning Blocks
	 * 
	 * @return
	 */
	public Integer getNumberOfUsers() {
		
		return findAllUsers().join().map(ul -> Integer.valueOf(ul.size())).orElse(0);
		
	}

//	//TODO how does a service method get affected by a static class such as Security Context.
	
//	public Boolean isUserAdmin(User userProfile) {
//		
//		List<GrantedAuthority> gauth = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().collect(Collectors.toList());
//		return gauth.get(0).getAuthority().equals(ConfigureUIServiceInitListener.ROLE_ADMIN);
//		
//	}

	/**
	 * 
	 * 
	 * @return a future that may have a list containing just the user names recorded in the database.
	 */
	public CompletableFuture<Optional<List<String>>> findAllUsers() {
		
		return userRepository.findAllUsers();
		
	}
	
}
