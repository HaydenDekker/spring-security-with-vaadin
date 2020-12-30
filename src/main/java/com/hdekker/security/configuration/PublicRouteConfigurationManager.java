package com.hdekker.security.configuration;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;


/**
 * All controllers implementing a URL route must
 * register their public routes so that form based
 * security is not applied. They must manage their own security
 * within the controller.
 * 
 * 
 * @author HDekker
 *
 */
@Configuration
public class PublicRouteConfigurationManager {

	Logger log = LoggerFactory.getLogger(PublicRouteConfigurationManager.class);
	
	public PublicRouteConfigurationManager(){
		
		log.info("Public route manager started");
		
	}
	
	 List<HasPublicRoutes> publicRoutes = new ArrayList<HasPublicRoutes>();
	 
	 public void addPublicRoute(HasPublicRoutes publicRoute) {
		 publicRoutes.add(publicRoute);
	 }
	 
	 public List<String> getPublicRoutes(){
		 
		 List<String> allRoutes = new ArrayList<String>();
		 
		 publicRoutes.forEach(route->{
			 
			 route.getPublicRoutes().stream().forEach(string-> allRoutes.add(string));
			 
			 
		 });
		 
		 return allRoutes;
		 
	 }
	
	
}
