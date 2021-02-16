package com.hdekker.security.component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ChipList extends HorizontalLayout{

	List<String> values;
	List<Consumer<List<String>>> changeEvents = new ArrayList<Consumer<List<String>>>();
	HorizontalLayout hl;
	TextField field;
	
	public ChipList(String listName, List<String> chips) {
		
		VerticalLayout vl = new VerticalLayout();
		vl.setPadding(false);
		add(vl);
		HorizontalLayout hlouter = new HorizontalLayout();
		hl = new HorizontalLayout();
		field = new TextField();
		values = new ArrayList<>(chips);
		
		chips.forEach(s->{
			addValue(s);
		});
		
		field.addKeyPressListener(e->{
			if(e.getKey().matches("Enter")) {
				addValue(field.getValue());
			}
			
		});
		
		Button check = new Button(new Icon(VaadinIcon.CHECK));
		
		check.addClickListener((e)->{
			addValue(field.getValue());
		});
		
		vl.add(new Label(listName), hlouter);
		hlouter.add(hl, field, check);
		
	}
	
	private void addNewChip(String val) {
		
		ChipWithDeleteButton c = new ChipWithDeleteButton(val);
		c.addDeleteClickListener((e)->{
			values.remove(val);
			hl.remove(c);
			fire();
		});
		hl.add(c);
		
	}
	
	private void addValue(String s) {
		
		values.add(s);
		addNewChip(s);
		field.setValue("");
		fire();
	}
	
	private void fire() {
		changeEvents.forEach(l->l.accept(values));
	}
	
	public void addChangeListener(Consumer<List<String>> l) {
		changeEvents.add(l);
	}
	
	public List<String> getValues() {
		
		return values;
		
	}
}
