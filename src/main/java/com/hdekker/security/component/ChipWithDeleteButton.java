package com.hdekker.security.component;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class ChipWithDeleteButton extends MaterialChip {
	
	Icon delete;
	
	public ChipWithDeleteButton(String string) {
		super(string);
		delete = new Icon(VaadinIcon.CLOSE_CIRCLE); 
		this.getElement().appendChild(delete.getElement());
		delete.getElement().getStyle().set("width", "24px");
		delete.getElement().getStyle().set("height", "12px");
		
	}
	
	public void addDeleteClickListener(ComponentEventListener<ClickEvent<Icon>> listener) {
		
		delete.addClickListener(listener);
		
	}
	
}
