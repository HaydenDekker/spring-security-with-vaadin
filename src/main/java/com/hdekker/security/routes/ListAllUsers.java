package com.hdekker.security.routes;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;

import com.hdekker.security.component.LogoutButton;
import com.hdekker.security.services.UserService;
import com.hdekker.security.services.data.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;


@Push
@Route(value = "user/list")
public class ListAllUsers extends VerticalLayout implements AfterNavigationObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Grid<String> users = new Grid<>();
	
	public ListAllUsers() {
		
		HorizontalLayout h = new HorizontalLayout();
		Button addUser = new Button("Add User");
		Div spacer = new Div();
		h.setFlexGrow(1, spacer);
		h.add(addUser, spacer, new LogoutButton());
		h.setAlignSelf(Alignment.STRETCH, h);
		h.setJustifyContentMode(JustifyContentMode.BETWEEN);
		h.setAlignItems(Alignment.BASELINE);
		add(h);
		
		add(users);
		
		addUser.addClickListener((e)-> UI.getCurrent().navigate(AddUser.class));
		
		users.addColumn(String::toString).setHeader("User Name").setFlexGrow(10);
			
		users.setItemDetailsRenderer(
				
				new ComponentRenderer<Div, String>(Div::new, (div,str) ->{
					
					TextField firstName = new TextField("First Name");
					div.add(firstName);
					
					CompletableFuture<Optional<User>> user = userService.findUserByUserName(str);
					
					user.thenAccept((usr)->{
						
						users.getUI().get().access(()->{
							
							//push all data to UI.
							firstName.setValue(usr.get().getFirstName());
							users.getUI().get().push();
						});
						
					});
					
				}));
		users.addColumn(new ComponentRenderer<Button, String>(Button::new, (button,str)->{
			button.setIcon(new Icon(VaadinIcon.CLOSE_CIRCLE));
			button.addClickListener(e->{
				userService.delete(str);
				setUsers();
			});
		})).setHeader("Delete").setFlexGrow(1);
		
	}

	@Autowired
	UserService userService;
	
	public void setUsers() {
		
		Notification n = new Notification();
		n.setText("Couldn't get users");
		// Need full user extraction implementation. Ahhh, I see.
		userService.findAllUsers().thenAccept((list)->{
			
			list.ifPresentOrElse((l)->{
				users.getUI().get().access(()->{
					users.setItems(l);
					users.getUI().get().push();
				});
			}, ()->{
				users.getUI().get().access(()->{
					n.open();
					users.getUI().get().push();
				});
			});
			
		});
		
	}
	
	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		
		
		setUsers();
		
	}
	
	
}
