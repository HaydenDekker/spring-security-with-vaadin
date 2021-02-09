package com.hdekker.security.configuration;


import com.hdekker.security.routes.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = LoggerFactory.getLogger(ConfigureUIServiceInitListener.class);
	
	@Override
	public void serviceInit(ServiceInitEvent event) {
		
		log.debug("Step 1: Add a UIInitListener - A user has accessed the for first time and the Vaadin service initialised event has been called.");
		event.getSource().addUIInitListener(uiEvent -> {
			
			log.debug("A new UI listener has been created. Adding a BeforeEnterListener to the UI to ensure security is checked.");
			final UI ui = uiEvent.getUI();
			ui.addBeforeEnterListener(this::beforeEnter);
			
		});
	}
	
	@Autowired
	ApplicationContext ctx;
	
	/**
	 * Reroutes the user if they are not authorized to access the view.
	 * 
	 * https://vaadin.com/learn/tutorials/securing-your-app-with-spring-security/fine-grained-access-control
	 *
	 * But added LoginView class to ensure we don't create a circular reroute.
	 *
	 * @param event
	 *            before navigation event with event details
	 */
	
	private void beforeEnter(BeforeEnterEvent event) {	
		
		log.debug("User has attempted to enter " + event.getNavigationTarget().getCanonicalName() + " for UI " + UI.getCurrent().getUIId());
		
		UserRedirect ur = ctx.getBean(UserRedirect.class);
		
		if(!SecurityUtils.isAccessGranted(event.getNavigationTarget())) { 
			if(SecurityUtils.isUserLoggedIn()) {
				// If user is logged in and route is Vaadin standard route then
				// they shall pass.
				if(event.getNavigationTarget().getPackageName().contains("vaadin.flow.router")) {
					log.debug("GRANTED - VAADIN INTERNAL - LOGGED IN.");
					return;
				}
				log.debug("NOT GRANTED - LOGGED IN.");
				event.rerouteToError(NotFoundException.class);
				return;
			}else {
				
				ur.setOptRedirect(Optional.of(event.getLocation().getPath()));
				// TODO should Vaadin be granted when not logged in?? hmmm
				log.debug("NOT GRANTED - NOT LOGGED IN.");
				
				event.rerouteTo(LoginView.class);
				return;
			}
			
		}
		log.debug("GRANTED");	
	}
}