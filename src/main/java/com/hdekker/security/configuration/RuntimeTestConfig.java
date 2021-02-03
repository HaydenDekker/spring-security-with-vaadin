package com.hdekker.security.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "tests")
public class RuntimeTestConfig {

	public boolean addPublicRoute; 

	public boolean isAddPublicRoute() {
		return addPublicRoute;
	}

	public void setAddPublicRoute(boolean addPublicRoute) {
		this.addPublicRoute = addPublicRoute;
	}
	
	
	
}
