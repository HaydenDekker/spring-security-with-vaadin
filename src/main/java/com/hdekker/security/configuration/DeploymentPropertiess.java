package com.hdekker.security.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(value = "security")
public class DeploymentPropertiess {

	
	String logoutSuccessTarget = SecurityConfiguration.LOGOUT_SUCCESS_URL;

	public String getLogoutSuccessTarget() {
		return logoutSuccessTarget;
	}

	public void setLogoutSuccessTarget(String logoutSuccessTarget) {
		this.logoutSuccessTarget = logoutSuccessTarget;
	}
	
	String loginSuccessRouteForDirectLoginPageNavigation = "/";

	public String getLoginSuccessRouteForDirectLoginPageNavigation() {
		return loginSuccessRouteForDirectLoginPageNavigation;
	}

	public void setLoginSuccessRouteForDirectLoginPageNavigation(String loginSuccessRouteForDirectLoginPageNavigation) {
		this.loginSuccessRouteForDirectLoginPageNavigation = loginSuccessRouteForDirectLoginPageNavigation;
	}
	
	Boolean enableSecurity;

	public Boolean getEnableSecurity() {
		return enableSecurity;
	}

	public void setEnableSecurity(Boolean enableSecurity) {
		this.enableSecurity = enableSecurity;
	}
	
	
	
	
	
	
}
