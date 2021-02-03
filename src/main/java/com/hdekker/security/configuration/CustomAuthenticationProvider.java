package com.hdekker.security.configuration;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hdekker.security.services.UserService;
import com.hdekker.security.services.data.User;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

	Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	/**
	 * Simply integrates the application User Service.
	 *  - Uses the userService to find the specified user and check
	 * that the credentials map.
	 *  - Responds with incorrect credentials message if failure occurs.
	 *  TODO need to provide integration test in SecurityContextTests.class
	 * 
	 */
	@Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
  
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
         
        CompletableFuture<Optional<User>> userCreds = userService.findUserByUserName(name);
        
        // Must get user now for config.
        Optional<User> authorisedUser = userCreds.join();
        
        return authorisedUser.map(usr-> {
	        
        	List<String> userRoles = userCreds.join().get().getRoles();
	        String userRole = userRoles.get(0);
	        List<GrantedAuthority> gas = AuthorityUtils.createAuthorityList(userRole);
	        if(passwordEncoder.matches(password, userCreds.join().get().getPassWord())) {
	    		
	        	return new UsernamePasswordAuthenticationToken(
	    			usr, password, gas);
	    	}else {
	    		throw new BadCredentialsException("Check username or password");
	    	}
	        	
        }).orElseThrow(()->new BadCredentialsException("Check username or password"));
    	
    	
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
	
}
