package com.hdekker.security.configuration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.hdekker.security.ConfigureUIServiceInitListener;
import com.hdekker.security.CustomAuthenticationProvider;
import com.hdekker.security.users.UserManagementService;
import com.hdekker.security.users.data.ApprovedUser;


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
		private static final String LOGOUT_SUCCESS_URL = "/login";
		
		public static final Boolean securityEnabled = true;
		
		Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);
		
	    @Autowired
	    private CustomAuthenticationProvider authProvider;
	    
	    @Autowired
	    UserManagementService userManagementService;
	 
	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.authenticationProvider(authProvider);
	    }
	    
	    @Autowired
	    PublicRouteConfigurationManager publicRouteConfigurationManager;
	    
		/**
		 * Require login to access internal pages and configure login form.
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// Not using Spring CSRF here to be able to use plain HTML for the login page
			
			List<ApprovedUser> approvedUsers = userManagementService.getAllApprovedUsers();
			
			if(approvedUsers==null || approvedUsers.size()==0){ 
				
				// first time start up.
				ApprovedUser apusr = userManagementService.getNewApprovedUser("admin", "admin", "admin", "admin", ConfigureUIServiceInitListener.ROLE_ADMIN);
				userManagementService.saveApprovedUser(apusr);
				
			}
			
			String[] allPublicRoutes = publicRouteConfigurationManager.getPublicRoutes().
			toArray(new String[publicRouteConfigurationManager.getPublicRoutes().size()]);
			log.info("Security Configuration found the following public routes " + allPublicRoutes);
			
			
			// TODO why did I do this???
			SavedRequestAwareAuthenticationSuccessHandler success = new SavedRequestAwareAuthenticationSuccessHandler();
			CustomRequestCache cc = new CustomRequestCache();
			success.setRequestCache(cc);
			
			// For testing. TODO move
			if(securityEnabled) {
			
				http.csrf().disable()
	
					// Register our CustomRequestCache, that saves unauthorized access attempts, so
					// the user is redirected after login.
					.requestCache().requestCache(cc)

					// Restrict access to our application.
					.and().authorizeRequests()

					// Allow all flow internal requests.
					.requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

					.antMatchers(allPublicRoutes)
							.permitAll()
					.anyRequest().authenticated()

					// Configure the login page.
					.and().formLogin().loginPage(LOGIN_URL).permitAll().loginProcessingUrl(LOGIN_PROCESSING_URL)
					.failureUrl(LOGIN_FAILURE_URL)
					
					.successHandler(success)

					// Configure logout
					.and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
			
			}else {
				
				http.csrf().disable()

					// Register our CustomRequestCache, that saves unauthorized access attempts, so
					// the user is redirected after login.
					.requestCache().requestCache(new CustomRequestCache())
	
					// Restrict access to our application.
					.and().authorizeRequests().anyRequest().permitAll();
				
				
			}
		}

		/* @Bean
		@Override
		public UserDetailsService userDetailsService() {
			UserDetails user =
					User.withUsername("user")
							.password("{noop}password")
							.roles("USER")
							.build();

			return new InMemoryUserDetailsManager(user);
		} */

		/**
		 * Allows access to static resources, bypassing Spring security.
		 */
		@Override
		public void configure(WebSecurity web) throws Exception {
		
			web.ignoring().antMatchers(
					// Vaadin Flow static resources
					"/VAADIN/**",

					// the standard favicon URI
					"/favicon.ico",

					// the robots exclusion standard
					"/robots.txt",

					// web application manifest
					"/manifest.webmanifest",
					"/sw.js",
					"/offline-page.html",

					// icons and images
					"/icons/**",
					"/images/**",

					// (development mode) static resources
					"/frontend/**",

					// (development mode) webjars
					"/webjars/**",

					// (development mode) H2 debugging console
					"/h2-console/**",

					// (production mode) static resources
					"/frontend-es5/**", "/frontend-es6/**");
		}
}
	

