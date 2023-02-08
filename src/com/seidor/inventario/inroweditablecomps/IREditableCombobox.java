package com.seidor.inventario.inroweditablecomps;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;

public class IREditableCombobox extends Div {
	
	private static final long serialVersionUID = 1L;
	public static final String ON_EDITABLE = "onEditable";
	public static final String ON_UPDATE = "onUpdate";
	
	private String text = "";
	private Boolean editable;
	private Combobox combobox;
	private Label label;
	
	public IREditableCombobox() {
		this.combobox = new Combobox();
		this.combobox.setZclass("gs-combobox");
		this.label = new Label();
		this.combobox.setWidth("100%");
		this.label.setWidth("100%");
		this.label.setParent(this);// Default show a label with text
		this.editable = false;
		initEditCtrl();
	}
	
	public IREditableCombobox(String text) {
		this();
		setText(text);
	}
	
	@SuppressWarnings("rawtypes")
	public IREditableCombobox(String text, ListModelList model) {
		this();
		this.combobox.setModel(model);
		setText(text);
	}
	
	@SuppressWarnings("rawtypes")
	public IREditableCombobox(String text, ListModelList model, ComboitemRenderer renderer) {
		this();
		this.combobox.setModel(model);
		setText(text);
		this.combobox.setItemRenderer(renderer);
	}
	
	@SuppressWarnings("rawtypes")
	public IREditableCombobox(String text, ListModelList model, ComboitemRenderer renderer, Constraint constraint) {
		this();
		this.combobox.setModel(model);
		setText(text);
		this.combobox.setItemRenderer(renderer);
		this.combobox.setConstraint(constraint);
	}
	
	@SuppressWarnings("rawtypes")
	public IREditableCombobox(String text, ListModelList model, ComboitemRenderer renderer, Constraint constraint, String labelClass) {
		this();
		this.combobox.setModel(model);
		this.label.setSclass(labelClass);
		setText(text);
		this.combobox.setItemRenderer(renderer);
		this.combobox.setConstraint(constraint);
	}
	
	// Getter and setters
	public void setText(String text) {
		this.text = text;
		this.combobox.setValue(text);
		this.label.setValue(text);
	}
	
	public String getText() {
		return text;
	}
	
	public Object getValue(){
		if (this.combobox.getSelectedItem() != null && this.combobox.getSelectedItem().getValue() != null){
			return this.combobox.getSelectedItem().getValue();
		}
		if (this.combobox.getSelectedIndex() >= 0 && this.combobox.getModel() != null){
			return this.combobox.getModel().getElementAt(this.combobox.getSelectedIndex());
		}
		return null;
	}
	
	public Combobox getCombobox(){
		return this.combobox;
	}
	
	public void setCombobox(Combobox combobox) {
		this.combobox = combobox;
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
			this.text = this.combobox.getValue();
			this.label.setValue(this.text);
		} else {
			Constraint constraint = this.combobox.getConstraint();
			this.combobox.setConstraint((Constraint)null);
			this.combobox.setValue(this.text);
			this.combobox.setConstraint(constraint);
			this.label.setValue(text);
		}
	}
	
	private void toggleEditable() {
		this.editable = !this.editable;
		if (this.editable) {
			this.label.detach();
			this.appendChild(this.combobox);
		} else {
			this.combobox.detach();
			this.appendChild(this.label);
		}
	}
	
	public Label getLabel(){
		return this.label;
	}
	
	
}