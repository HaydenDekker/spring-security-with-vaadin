package com.hdekker.security.routes;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import com.hdekker.security.services.UserService;
import com.hdekker.security.services.data.User;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;


@Push
@Route(value = "users/list")
public class ListAllUsers extends VerticalLayout implements AfterNavigationObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Grid<String> users = new Grid<>();
	
	public ListAllUsers() {
		
		add(users);
		
		users.addColumn(String::toString).setHeader("User Name");	
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
		
	}

	@Autowired
	UserService userService;
	
	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		
		Notification n = new Notification();
		
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
	
	
}
