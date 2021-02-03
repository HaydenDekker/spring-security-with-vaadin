package com.hdekker.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.context.annotation.Configuration;

import com.vaadin.flow.spring.VaadinMVCWebAppInitializer;

/**
 * Using this to try get Vaadin registered with Spring Mvc in order to use MockMvc in the spring tests.
 * 
 * Spring boot doesn't utilise this. It uses a servletcontextinitialiser.
 * 
 * @author HDekker
 *
 */
@Configuration
@Deprecated
public class TestSpringMvcVaadinRegistration extends VaadinMVCWebAppInitializer{

	@Override
	protected Collection<Class<?>> getConfigurationClasses() {
		
		return Arrays.asList();
	}

}
