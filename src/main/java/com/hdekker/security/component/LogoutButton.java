package com.hdekker.security.component;

import com.vaadin.flow.component.html.Anchor;

public class LogoutButton extends Anchor{

	public LogoutButton() {
		setText("logout");
		setHref("logout");
	}
	
}
