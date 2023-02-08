package com.seidor.inventario.inroweditablecomps;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

public class IREditablePassbox extends Div {
	
	private static final long serialVersionUID = 1L;
	public static final String ON_EDITABLE = "onEditable";
	public static final String ON_UPDATE = "onUpdate";
	
	private String text = "";
	private Boolean editable;
	private Textbox textbox;
	private Label label;
	
	public IREditablePassbox() {
		this.textbox = new Textbox();
		this.textbox.setSclass("form-control");
		this.textbox.setMaxlength(255);
		this.textbox.setWidth("100%");
		this.textbox.setType("password");
		this.label = new Label();
		this.label.setWidth("100%");
		this.label.setParent(this);// Default show a label with text
		this.editable = false;
		initEditCtrl();
	}
 
	public IREditablePassbox(String text) {
		this();
		setText(text);
	}
	
	public IREditablePassbox(String text, Constraint constraint) {
		this();
		setText(text);
		this.textbox.setConstraint(constraint);
	}
	
	// Getter and setters
	public Textbox getTextbox() {
		return this.textbox;
	}
	
	public Label getLabel(){
		return this.label;
	}
	
	public void setText(String text) {
		this.text = text;
		this.textbox.setValue("");
		if (text != null && text.trim().length() > 0) {
			this.label.setValue("*****");
		}
		else {
			this.label.setValue("");
		}
	}
 
	public String getText() {
		return text;
	}
	
	public String getValue(){
		return this.textbox.getValue();
	}
 
	// Initialize the listener of the whole component and the textbox in it
	private void initEditCtrl() {
		this.addEventListener(ON_EDITABLE, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				toggleEditable();
			}
		});
		
		this.addEventListener(ON_UPDATE, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				applyChanges((Boolean) event.getData());
				toggleEditable();
			}
		});
		
	}
 
	// Replace textbox/label with label/textbox
	private void applyChanges(Boolean applyChange) {
		//if apply changes then set the value in
		if (applyChange) {
			setText(this.textbox.getValue());
		} else {
			setText(this.text);
		}
	}
	
	private void toggleEditable() {
		this.editable = !this.editable;
		if (this.editable) {
			this.label.detach();
			this.appendChild(this.textbox);
		} else {
			this.textbox.detach();
			this.appendChild(this.label);
		} 
	}
	
}
