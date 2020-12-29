package com.hdekker.security;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hdekker.security.users.UserManagementService;
import com.hdekker.security.users.data.ApprovedUser;
import com.hdekker.security.users.data.UserCredentials;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

	Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	
	@Autowired
	//UserCredentialsRepository userCredentialsRepository;
	UserManagementService userManagementService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
  
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
         
        Optional<UserCredentials> userCreds = userManagementService.findUserByUserName(name);
        if(!userCreds.isPresent()) return null;
        List<String> userCredList = userCreds.get().getCredientials();
        String userRole = userCredList.get(0);
        
        List<GrantedAuthority> gas = AuthorityUtils.createAuthorityList(userRole);
        	
    	if(passwordEncoder.matches(password, userCreds.get().getPassWord())) {
    		
    		ApprovedUser authorisedUser = userManagementService.getApproveUserWithCredentials(userCreds.get());
    		return new UsernamePasswordAuthenticationToken(
    			authorisedUser, password, gas);
    	}else {
    		// throw error incorrect password
    		return null;
    	}
    	
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
	
}
