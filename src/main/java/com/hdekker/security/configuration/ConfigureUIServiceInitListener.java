package com.hdekker.security.configuration;


import com.hdekker.security.routes.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

	public static final String ROLE_ADMIN = "ADMIN";
	public static final String ROLE_AUTHOR = "AUTHOR";
	
	Logger log = LoggerFactory.getLogger(ConfigureUIServiceInitListener.class);
	
	@Override
	public void serviceInit(ServiceInitEvent event) {
		
		log.info("Vaain service initialised event called " + event.getSource());
		
		event.getSource().addUIInitListener(uiEvent -> {
			final UI ui = uiEvent.getUI();
			ui.addBeforeEnterListener(this::beforeEnter);
			
		});
	}

	/**
	 * Reroutes the user if (s)he is not authorized to access the view.
	 *
	 * @param event
	 *            before navigation event with event details
	 */
	private void beforeEnter(BeforeEnterEvent event) {	
		
		if(SecurityConfiguration.securityEnabled) {
		
			// Unless view adopts a PublicRouteInterface we want to restrict navigation
			if (!LoginView.class.equals(event.getNavigationTarget())
			    && !SecurityUtils.isUserLoggedIn()) {
				
				String route = RouteConfiguration.forApplicationScope().getUrl((Class<? extends com.vaadin.flow.component.Component>) event.getNavigationTarget());
				
				if(route.equals("") || route.contains("stories")) {
					 
					return;
				}
				event.rerouteTo(LoginView.class);
				return;
			}
		}
		
		
	}
}