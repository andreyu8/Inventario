package com.seidor.inventario.inroweditablecomps;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Button;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Span;

public abstract class IREditableBandbox extends Div {
	
	private static final long serialVersionUID = 1L;
	public static final String ON_EDITABLE = "onEditable";
	public static final String ON_UPDATE = "onUpdate";
	
	private String text = "";
	private Object value="";
	private Boolean editable;
	private Boolean addEnabled;
	private Bandbox bandbox;
	private Span span;
	private Button addbutton;
	private Bandpopup bandpopup;
	private Label label;
	private Listbox listbox;
	
	public IREditableBandbox(Boolean addEnabled) {
		this.setZclass("input-group");
		this.addEnabled = (addEnabled == null) ? Boolean.FALSE : addEnabled;
		if (this.addEnabled) {
			this.span = new Span();
			this.span.setZclass("input-group-btn");
			this.addbutton = new Button();
			this.addbutton.setZclass("btn btn-default");
			this.addbutton.setIconSclass("z-icon-plus-circle");
			this.addbutton.setTooltiptext("Añadir nuevo");
			this.addbutton.setParent(span);
			this.addbutton.addEventListener(Events.ON_CLICK, onAddNew());
		}
		this.bandbox = new Bandbox();
		this.bandbox.setZclass("gs-combobox");
		this.bandbox.setAutodrop(true);
		this.label = new Label();
		this.bandbox.setWidth("100%");
		this.label.setWidth("100%");
		this.label.setParent(this);// Default show a label with text
		this.editable = false;
		this.bandbox.addEventListener(Events.ON_OPEN, new OpenEventBandbox(this.bandbox));
		this.bandbox.addEventListener(Events.ON_CHANGING, onChanging());

	    this.bandpopup = new Bandpopup();
	    this.bandbox.appendChild(bandpopup);	    
	    this.listbox= new Listbox();	
		this.listbox.setWidth("480px");
		this.listbox.addEventListener( Events.ON_SELECT, new SelectEventBandbox(this));
		this.listbox.setMold("paging");
		this.listbox.setPageSize(5);
		this.listbox.setAutopaging(false);
		this.listbox.setParent(bandpopup);
		this.bandpopup.appendChild(listbox);
		
		initEditCtrl();
	}
	
	@SuppressWarnings("rawtypes")
	public IREditableBandbox(String text, Object value, String[] headers, ListitemRenderer render, Constraint constraint, Boolean addEnabled) {
		this(addEnabled);
		this.setText(text);
		this.setValue(value);
		this.createHeads(headers);
		this.bandbox.setConstraint(constraint);
	    this.listbox.setItemRenderer(render);
	}
	
	@SuppressWarnings("rawtypes")
	public IREditableBandbox(String text, Object value, String[] headers, ListitemRenderer render) {
		this(text, value, headers, render , null, null);
	}

	@SuppressWarnings("rawtypes")
	public IREditableBandbox(String text, Object value, String[] headers, ListitemRenderer render, Boolean addEnabled) {
		this(text, value, headers, render , null, addEnabled);
	}
	
		
	// Getter and setters
	public void setText(String text) {
		this.text = text;
		this.bandbox.setValue(text);
		this.label.setValue(text);
	}
	
	public String getText() {
		return text;
	}
		
	public void setValue(Object value) {
		this.value = value;
		this.bandbox.setAttribute("value", value);
	}

	public Object getValue(){
		if (this.bandbox.getAttribute("value")!=null){
			return this.bandbox.getAttribute("value");
		}
		return null;
	}
	
	public Bandbox getBandbox() {
		return bandbox;
	}

	public void setBandbox(Bandbox bandbox) {
		this.bandbox = bandbox;
	}

	public Listbox getListbox() {
		return this.listbox;
	}

	public void setListbox(Listbox listbox) {
		this.listbox = listbox;
	}

	public static String getOnEditable() {
		return ON_EDITABLE;
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
			this.text = this.bandbox.getValue();
			this.label.setValue(this.text);
		} else {
			Constraint constraint = this.bandbox.getConstraint();
			this.bandbox.setConstraint((Constraint)null);
			this.bandbox.setValue(this.text);
			this.bandbox.setAttribute("value", this.value);
			this.bandbox.setConstraint(constraint);
			this.label.setValue(text);
		}
	}
	
	private void toggleEditable() {
		this.editable = !this.editable;
		if (this.editable) {
			this.label.detach();
			this.appendChild(this.bandbox);
			if(this.addEnabled)
				this.appendChild(this.span);
		} else {
			if(this.addEnabled)
				this.span.detach();
			this.bandbox.detach();
			this.appendChild(this.label);
		}
	}
	
	public Label getLabel(){
		return this.label;
	}
	
	public void createHeads(String[] heads){
		Listhead listhead= new Listhead();
		for(String head : heads){
			Listheader header= new Listheader(head);
			listhead.appendChild(header);
		}
		this.listbox.appendChild(listhead);

	}
	
	public void clear(){
		bandbox.setText("");
		bandbox.getAttributes().clear();
	}
		
	public abstract EventListener<InputEvent> onChanging();
	
	public abstract EventListener<Event > onAddNew();
	
}

class SelectEventBandbox implements EventListener<Event>{
	private IREditableBandbox editableBandbox; 
	
	public SelectEventBandbox(IREditableBandbox editableBandbox){
		this.editableBandbox= editableBandbox;
	}
	
	public void onEvent(Event event) throws Exception {
		Listbox listbox =editableBandbox.getListbox();
		Bandbox bandbox =editableBandbox.getBandbox();

		if(listbox.getSelectedItem()!=null){		
			Listitem listitem= listbox.getSelectedItem();
			Listcell listcell=  (Listcell) listitem.getFirstChild();
			Object value = (Object) listbox.getModel().getElementAt( listbox.getSelectedIndex());
			bandbox.setText(listcell.getLabel());
			bandbox.setAttribute("value", value);
			bandbox.close();
			editableBandbox.setValue(value);
		}
	}
}

class OpenEventBandbox implements EventListener<Event>{
	private Bandbox bandbox; 
	
	public OpenEventBandbox(Bandbox bandbox){
		this.bandbox= bandbox;
	}
	
	public void onEvent(Event event) throws Exception {
		if (event instanceof OpenEvent) {
			OpenEvent openEvent = (OpenEvent) event;
			if (openEvent.isOpen()) {
				bandbox.open();
			} else {
				bandbox.close();
			}
		}
	}
}