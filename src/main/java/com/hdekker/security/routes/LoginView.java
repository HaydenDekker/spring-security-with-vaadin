package com.hdekker.security.routes;

import org.springframework.security.access.annotation.Secured;

import com.hdekker.security.configuration.SecurityBaseRoles;
import com.hdekker.security.configuration.SecurityUtils;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
@Secured(value = {SecurityBaseRoles.PUBLIC})
public class LoginView extends VerticalLayout {
	
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
}