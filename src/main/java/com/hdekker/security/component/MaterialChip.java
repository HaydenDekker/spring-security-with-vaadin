package com.hdekker.security.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

@NpmPackage(value = "@authentic/mwc-chips", version = "0.9.0")
@Tag("mwc-chip")
@JsModule("@authentic/mwc-chips/mwc-chip.js")
public class MaterialChip extends Component{
	
	public MaterialChip(String text) {
		this.getElement().setText(text);
		
	}
}
