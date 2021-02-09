package com.hdekker.security.configuration;

import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;

@Component
//@VaadinSessionScope
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class UserRedirect {

	Optional<String> optRedirect = Optional.empty();

	public Optional<String> getOptRedirect() {
		return optRedirect;
	}

	public void setOptRedirect(Optional<String> optRedirect) {
		this.optRedirect = optRedirect;
	}

	

	
	
	
	
}
