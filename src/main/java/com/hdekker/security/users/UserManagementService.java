package com.hdekker.security.users;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hdekker.security.ConfigureUIServiceInitListener;
import com.hdekker.security.users.data.ApprovedUser;
import com.hdekker.security.users.data.User;
import com.hdekker.security.users.data.UserCredentials;
import com.hdekker.security.users.repo.ApprovedUserRepository;
import com.hdekker.security.users.repo.UserCredentialsRepository;
import com.hdekker.security.users.repo.UserRepository;
import com.hdekker.security.users.repo.UserRequestRepository;


@Service
@DependsOn("passwordEncoder")
public class UserManagementService {

	@Autowired
	UserCredentialsRepository userCredentialsRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ApprovedUserRepository approvedUserRepository;
	
	@Autowired
	UserRequestRepository userRequestRepository;
	
	UserManagementService(){
		
		
	}
	
	// TODO user credentials should be saved before saving approved user
	private UserCredentials saveUserCredentials(UserCredentials userCredentials){
		
		return userCredentialsRepository.save(userCredentials);
		
	}
	
	public ApprovedUser saveApprovedUser(ApprovedUser approvedUser) {
		
		UserCredentials us = saveUserCredentials(approvedUser.getUserCredentials());
		User user = saveUser(approvedUser.getUser());
		approvedUser.setUser(user);
		approvedUser.setUserCredentials(us);
		approvedUser.setApprovedOn(LocalDateTime.now());
		
		return approvedUserRepository.save(approvedUser);
		
				
	}

	private User saveUser(User user) {
		
		return userRepository.save(user);
	}
	
	public ApprovedUser stampLastUserActiveDate(ApprovedUser approvedUser) {
		
		approvedUser.setLastActiveOn(LocalDateTime.now());
		return saveApprovedUser(approvedUser);
		
	}
	
	public List<ApprovedUser> getAllApprovedUsers(){
		
		List<ApprovedUser> users = approvedUserRepository.findAll().stream()
				.filter(user->!user.getIsActive().equals(false)).collect(Collectors.toList());
		
		// TODO move database dependencies our of class
//		users.stream().forEach(user->{
//			
//			Hibernate.initialize(user.getUser());
//			Hibernate.initialize(user.getUserCredentials());
//			
//		});
		
		return users;
		
	}

	public ApprovedUser getNewApprovedUser(String name, String surname, String email, String password, String role) {
		
		ApprovedUser ap = new ApprovedUser();
		User user = new User();
		UserCredentials uc = new UserCredentials();
		ap.setUser(user);
		ap.setUserCredentials(uc);
		ap.setIsActive(true);
		
		user.setFirstName(name);
		user.setSecondName(surname);
		user.setEmail(email);
		user.setUserName(email); 
		
		// what did I mean by credentials.... Roles???
		//uc.setCredientials(Arrays.asList(role));
		uc.setPassWord(passwordEncoder.encode(password));
		uc.setUserName(email);
		
		return ap;
		
	}

	public void deleteUser(ApprovedUser approvedUser) {
		
		UserCredentials uc = approvedUser.getUserCredentials();
		approvedUser.setIsActive(false);
		approvedUser.setUserCredentials(null);
		approvedUserRepository.save(approvedUser);
		userCredentialsRepository.delete(uc);
		
	}

	public Optional<UserCredentials> findUserByUserName(String name) {
		
			UserCredentials us = userCredentialsRepository.findFirstByUserName(name);
//			if(us!=null) {
//				Hibernate.initialize(us.getCredientials());
//				return Optional.of(us);
//			}
			return Optional.empty();
		
	}
	
	public ApprovedUser getApproveUserWithCredentials(UserCredentials userCredentials) {
		
		return approvedUserRepository.findFirstByUserCredentialsUserName(userCredentials.getUserName());
		
	}

	//TODO how does a service method get affected by a static class such as Security Context.
	public Boolean isUserAdmin(ApprovedUser userProfile) {
		
		List<GrantedAuthority> gauth = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().collect(Collectors.toList());
		return gauth.get(0).getAuthority().equals(ConfigureUIServiceInitListener.ROLE_ADMIN);
		
	}
	
}
