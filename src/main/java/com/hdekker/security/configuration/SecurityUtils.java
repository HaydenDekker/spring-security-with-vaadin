package com.hdekker.security.configuration;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.server.HandlerHelper.RequestType;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.ApplicationConstants;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SecurityUtils takes care of all such static operations that have to do with
 * security and querying rights from different beans of the UI.
 *
 */
public final class SecurityUtils {
	
	private static Map<Class<? extends Component>, List<String>> viewAuth = new HashMap<>();
	
	public static void addRouteAuthorisation(Class<? extends Component> clazz, List<String> auth) {
		viewAuth.put(clazz, auth);
	}
	
	private SecurityUtils() {
		// Util methods only
	}

	/**
	 * Tests if the request is an internal framework request. The test consists of
	 * checking if the request parameter is present and if its value is consistent
	 * with any of the request types know.
	 *
	 * @param request
	 *            {@link HttpServletRequest}
	 * @return true if is an internal framework request. False otherwise.
	 */
	static boolean isFrameworkInternalRequest(HttpServletRequest request) {
		final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
		return parameterValue != null
				&& Stream.of(RequestType.values()).anyMatch(r -> r.getIdentifier().equals(parameterValue));
	}

	/**
	 * Tests if some user is authenticated. As Spring Security always will create an {@link AnonymousAuthenticationToken}
	 * we have to ignore those tokens explicitly.
	 */
	public static boolean isUserLoggedIn() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null
				&& !(authentication instanceof AnonymousAuthenticationToken)
				&& authentication.isAuthenticated();
	}
	
	public static void logout(VaadinSession vaadinSession) {
		
		
		vaadinSession.close();
		SecurityContextHolder.getContext().setAuthentication(null);

		
	}

	public static boolean userHasRoles(String...role) { 
		
		List<String> roles = Arrays.asList(role);
		List<GrantedAuthority> gauth = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().collect(Collectors.toList());
		return gauth.stream().filter(r->roles.contains(r.getAuthority())).findAny().isPresent();
		
	}
	
	/**
	 * As per Vaadin tutorial, except:
	 * - false by default
	 * - Uses static method to declare security so that modules do not require
	 *   @Secure() annotation. The programmer should use this interface to 
	 *   declare static config in a top level module.
	 * 
	 * 
	 * @param securedClass
	 * @return
	 */
	public static boolean isAccessGranted(Class<?> securedClass) {
		
	    // Allow if no roles are required.
		if (!viewAuth.containsKey(securedClass)) {
	       return false;
	    }

	    // lookup needed role in user roles
	    // Anonymous users can always see must be assigned an anonymous role. How?
	    List<String> allowedRoles = Optional.ofNullable(viewAuth.get(securedClass)).orElse(new ArrayList<>());
	    if(allowedRoles.contains(SecurityBaseRoles.PUBLIC)) return true;
	    
	    Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
	    return userAuthentication.getAuthorities().stream() // 
	            .map(GrantedAuthority::getAuthority)
	            .anyMatch(allowedRoles::contains);
	}
}
