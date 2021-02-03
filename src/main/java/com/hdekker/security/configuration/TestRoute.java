package com.hdekker.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.startup.ApplicationRouteRegistry;


/**
 * Trying to provide runtime example easily. But no need, maybe a simple app demo with it added as a dependency.
 * 
 * TODO App DEMO.
 * 
 * @author HDekker
 *
 */
@Deprecated
@Configuration
public class TestRoute {

	
	@Autowired
	RuntimeTestConfig runTestConfig;
	
	@Secured(SecurityBaseRoles.PUBLIC)
	class TestComponent extends Div {

		public TestComponent() {
			add(new H2("This is a public test route"));
		}

	}
	
	@Bean
	TestComponent testComp() {
		return new TestComponent();
	}
	
	@Autowired
	ApplicationRouteRegistry rr;

	public TestRoute(RuntimeTestConfig runTestConfig, ApplicationRouteRegistry rr) {

		this.runTestConfig = runTestConfig;
		
		if (runTestConfig.isAddPublicRoute()) {
			RouteConfiguration.forRegistry(rr).setRoute("test", TestComponent.class);
		}
	}

}
