package com.hdekker.security.configuration;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.hdekker.security.routes.AddUser;
import com.hdekker.security.routes.ListAllUsers;
import com.hdekker.security.routes.LoginView;
import com.hdekker.security.services.UserService;
import com.hdekker.security.services.data.User;


	/**
	 * Configures spring security, doing the following:
	 * <li>Bypass security checks for static resources,</li>
	 * <li>Restrict access to the application, allowing only logged in users,</li>
	 * <li>Set up the login form</li>
	 */
	@EnableWebSecurity
	@Configuration
	@DependsOn("passwordEncoder")
	public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		private static final String LOGIN_PROCESSING_URL = "/login";
		private static final String LOGIN_FAILURE_URL = "/login";
		private static final String LOGIN_URL = "/login";
		public static final String LOGOUT_SUCCESS_URL = "/login";
		
		public static final Boolean securityEnabled = true;
		
		Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);
		
	    @Autowired
	    private CustomAuthenticationProvider authProvider;
	    
	    @Autowired
	    UserService userManagementService;
	 
	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.authenticationProvider(authProvider);
	    }
	    
	    // Vaadin not active for this thread. Could a spring session bean do the trick
	    @Autowired
	    ApplicationContext ctx;
	    
	    AuthenticationSuccessHandler success = new AuthenticationSuccessHandler() {
			
	    	// wanted this out of the login view to avoid having to 
	    	// redirect to the login page after auth to redirect to the
	    	// intended page.
	    	
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				
				// may not be set so go to home page if failed. TODO set this config.
				UserRedirect ur = ctx.getBean(UserRedirect.class);
				response.sendRedirect(ur.getOptRedirect().orElse(dp.getLoginSuccessRouteForDirectLoginPageNavigation()));
				ur.setOptRedirect(Optional.empty());
				
			}
		};
	    
		@Autowired
		DeploymentPropertiess dp;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			// Configure auth for security module static views
			SecurityUtils.addRouteAuthorisation(LoginView.class, Arrays.asList(SecurityBaseRoles.PUBLIC));
			SecurityUtils.addRouteAuthorisation(ListAllUsers.class, Arrays.asList(SecurityBaseRoles.ADMIN));
			SecurityUtils.addRouteAuthorisation(AddUser.class, Arrays.asList(SecurityBaseRoles.ADMIN));
			
			Integer users = userManagementService.getNumberOfUsers();
			if(users.equals(0)) {
				CompletableFuture<Optional<User>> user = userManagementService.createUser("Admin", "Admin", "Admin", "admin", "admin", Arrays.asList("ADMIN"));
				user.get();
			}
			
			// TODO why did I do this???
			//SavedRequestAwareAuthenticationSuccessHandler success = new SavedRequestAwareAuthenticationSuccessHandler();
			CustomRequestCache cc = new CustomRequestCache();
			//success.setRequestCache(cc);
		
			http
				.csrf().disable()

				// Register our CustomRequestCache, that saves unauthorized access attempts, so
				// the user is redirected after login.
				//.requestCache().requestCache(cc)
				// This isn't being triggered because I allow all by default.

				// Restrict access to our application.
				.authorizeRequests()

				// Allow all flow internal requests.
				.requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

				// Will handle Auth in Vaadin realm.
				.anyRequest().permitAll() 
			
				// Configure the login page.
				.and().formLogin().loginPage(LOGIN_URL).permitAll().loginProcessingUrl(LOGIN_PROCESSING_URL)
				.failureUrl(LOGIN_FAILURE_URL)
				//.defaultSuccessUrl(LOGIN_URL)
				
				.successHandler(success)

				// Configure logout
				.and().logout().logoutSuccessUrl(dp.getLogoutSuccessTarget());
			
		}

		/**
		 * Allows access to static resources, bypassing Spring security.
		 */
		@Override
		public void configure(WebSecurity web) throws Exception {
		
			web.ignoring().antMatchers(
					SecurityUtils.staticResources);
		}
}
	

