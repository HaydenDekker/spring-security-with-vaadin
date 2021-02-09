package com.hdekker.security.routes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.hdekker.security.configuration.SecurityUtils;
import com.hdekker.security.configuration.UserRedirect;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * bg color #67aae4;
 * 
 * @author hdekker
 *
 */
@Route(value = LoginView.ROUTE)
@PageTitle("Login")
@NpmPackage(value = "@polymer/iron-form", version = "3.0.1")
@JsModule("@polymer/iron-form/iron-form.js")
public class LoginView extends VerticalLayout implements BeforeEnterObserver, AfterNavigationObserver{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String ROUTE = "login";
	
	Div centerLayout = new Div();
	
	Anchor requestAccess = new Anchor();
	
	Div popup = new Div();
	
	LoginForm component = new LoginForm();

	public LoginView() {
		
		setSizeFull();
		getElement().getStyle().set("align-items", "center");
		getElement().getStyle().set("justify-content", "center");
		
		add(centerLayout);
		centerLayout.setId("center-layout");
		Div header = new Div();
		header.setClassName(AppStyle.HOZLAYOUT);
		centerLayout.add(header);
		
		requestAccess.setText("sign up");
		requestAccess.setClassName("align-start");
		
		getElement().getStyle().set("background-color", "#67aae4");	
		setClassName("login-view");
		
		centerLayout.add(component);
		component.setAction("login");
		component.getElement().getStyle().set("background-color", "#e2c9c9");
		

	}
	

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		
		
		
	}
	
	@Autowired
	UserRedirect ur;

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		
		
//		if(ur.getOptRedirect().isPresent() && SecurityUtils.isUserLoggedIn()) {
//			
//			// re-route to failed page.
//			UI.getCurrent().getPage().setLocation(ur.getOptRedirect().get());
//			event.rerouteTo(ur.getOptRedirect().get());
//			ur.setOptRedirect(Optional.empty());
//			
//		}
		
	}
}