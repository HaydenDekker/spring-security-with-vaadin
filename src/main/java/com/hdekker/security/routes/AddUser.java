package com.hdekker.security.routes;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import com.hdekker.security.component.ChipList;
import com.hdekker.security.component.ChipWithDeleteButton;
import com.hdekker.security.component.MaterialChip;
import com.hdekker.security.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("user/add")
public class AddUser extends VerticalLayout{

	UserFlowState usf = new UserFlowState();
	
	FormLayout usrForm = new FormLayout();
	
	TextField userName = new TextField("User Name");
	TextField firstName = new TextField("First Name");
	TextField surname = new TextField("Surname");
	EmailField email = new EmailField("Email");
	
	// confirm pw for first time user
	PasswordField pw = new PasswordField("Password");
	PasswordField confirmPassword = new PasswordField("Confirm Password");
	
	ChipList roles = new ChipList("Roles", Arrays.asList());
	
	@Autowired
	UserService service;
	
	AddUser(){
		
		add(usrForm);
		
		usrForm.add(userName);
		usrForm.add(firstName);
		usrForm.add(surname);
		usrForm.add(email);
		usrForm.add(pw);
		usrForm.add(confirmPassword);
		usrForm.add(roles);
		
		Button addUser = new Button("Add User");
		
		add(addUser);
		setAlignSelf(Alignment.END, addUser);
		
		addUser.addClickListener((e)->{
			
			// DB integration
			service.createUser(firstName.getValue(), surname.getValue(),email.getValue(), userName.getValue(), pw.getValue(), roles.getValues());
			UI.getCurrent().navigate(ListAllUsers.class);
			
		});
		
	}
	
}
