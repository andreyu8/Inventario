package com.seidor.inventario.inroweditablecomps;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;

public class IREditableIntbox extends Div {
	
	private static final long serialVersionUID = 1L;
	public static final String ON_EDITABLE = "onEditable";
	public static final String ON_UPDATE = "onUpdate";
	
	private Integer num;
	private Boolean editable;
	private Intbox intbox;
	private Label label;
	private String placeholder;
	
	public IREditableIntbox() {
		this.intbox = new Intbox();
		this.intbox.setSclass("form-control");
        this.label = new Label();
        this.intbox.setStyle("overflow: auto;");
        this.intbox.setWidth("100%");
        this.label.setWidth("100%");
        this.label.setParent(this);// Default show a label with text
        this.editable = false;
        this.placeholder = "";
        initEditCtrl();
    }
 
    public IREditableIntbox(Integer num) {
        this();
        setNumber(num);
    }
    
    public IREditableIntbox(Integer num, Constraint constraint) {
        this();
        setNumber(num);
        this.intbox.setConstraint(constraint);
    }

    public IREditableIntbox(Integer num, String placeholder, Constraint constraint) {
    	this(num, constraint);
    	if(placeholder != null) {
    		this.placeholder = " " + placeholder;
    		this.intbox.setPlaceholder(placeholder);
    		this.label.setValue(num.toString() + this.placeholder);
    	}
    }
 
    // Getter and setters
    public void setNumber(Integer num) {
        this.num = num;
        	this.intbox.setValue(num);
        if (num != null)
        	this.label.setValue(num.toString() + this.placeholder);
        else
        	this.label.setValue("");
    }
 
    public Integer getNumber() {
        return this.num;
    }
    
    public Integer getValue(){
    	return this.intbox.getValue();
    }
    
    public void setMaxLength(Integer maxlength){
    	this.intbox.setMaxlength(maxlength);
    }
    
    public Intbox getIntbox(){
    	return this.intbox;
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
    		this.num = this.intbox.getValue();
    		if (this.num != null)
    			this.label.setValue(this.num.toString() + this.placeholder);
    		else
    			this.label.setValue("");
        } else {
        	Constraint constraint = this.intbox.getConstraint();
        	this.intbox.setConstraint((Constraint)null);
        	this.intbox.setValue(this.num);
        	this.intbox.setConstraint(constraint);
        	if (this.num != null)
    			this.label.setValue(this.num.toString() + this.placeholder);
    		else
    			this.label.setValue("");
        }
    }
    
    private void toggleEditable() {
    	this.editable = !this.editable;
    	if (this.editable) {
            this.label.detach();
            this.appendChild(this.intbox);
        } else {
            this.intbox.detach();
            this.appendChild(this.label);
        } 
    }
    
}
