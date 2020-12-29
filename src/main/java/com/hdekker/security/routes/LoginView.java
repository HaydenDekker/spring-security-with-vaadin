package com.hdekker.security.routes;

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
		
		//H1 rsaApplicationsHeader = new H1("RSApps");
		//H3 beta = new H3("beta");
		
		/*TextField userNameTextField = new TextField();
		userNameTextField.focus();
		userNameTextField.setPlaceholder("email");
		userNameTextField.getElement().setAttribute("name", "username");
		userNameTextField.setAutocomplete(Autocomplete.USERNAME);
		PasswordField passwordField = new PasswordField();
		passwordField.setPlaceholder("password");
		passwordField.getElement().setAttribute("name", "password");
		passwordField.setAutocomplete(Autocomplete.NEW_PASSWORD);
		Button submitButton = new Button("Login");
		submitButton.setId("submitbutton");*/
		//UI.getCurrent().executeJs();
		//UI.getCurrent().getPage().executeJavaScript("document.getElementById('submitbutton').addEventListener('click', () => document.getElementById('ironform').submit());");

		/*FormLayout formLayout = new FormLayout();
		formLayout.add(userNameTextField, passwordField, submitButton);

		Element formElement = new Element("form");
		formElement.setAttribute("method", "POST");
		formElement.setAttribute("action", "login");
		formElement.appendChild(formLayout.getElement());

		Element ironForm = new Element("iron-form");
		ironForm.setAttribute("id", "ironform");
		ironForm.setAttribute("allow-redirect", true);
		ironForm.appendChild(formElement);*/

		add(centerLayout);
		centerLayout.setId("center-layout");
		Div header = new Div();
		header.setClassName(AppStyle.HOZLAYOUT);
		//header.add(rsaApplicationsHeader, beta);
		centerLayout.add(header);
		//centerLayout.getElement().appendChild(ironForm);
		
		requestAccess.setText("sign up");
		requestAccess.setClassName("align-start");
		//centerLayout.add(requestAccess);
		
		getElement().getStyle().set("background-color", "#67aae4");
		
		setClassName("login-view");
		
		centerLayout.add(component);
		component.setAction("login");
		component.getElement().getStyle().set("background-color", "#e2c9c9");
		

	}
}