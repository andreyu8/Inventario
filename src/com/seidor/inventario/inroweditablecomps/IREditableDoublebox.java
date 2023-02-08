package com.seidor.inventario.inroweditablecomps;

import com.seidor.inventario.util.NumberFormatUtil;
import com.seidor.inventario.validation.DoubleboxValidator;

import java.util.Locale;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;

public class IREditableDoublebox extends Div {
	
	private static final long serialVersionUID = 1L;
	public static final String ON_EDITABLE = "onEditable";
	public static final String ON_UPDATE = "onUpdate";
	
	private Double num;
	private Boolean editable;
	private Doublebox doublebox;
	private Label label;
	
	public IREditableDoublebox() {
		this.doublebox = new Doublebox();
		this.doublebox.setSclass("form-control");
		this.doublebox.setConstraint(new DoubleboxValidator());
        this.label = new Label();
        this.doublebox.setWidth("100%");
        this.doublebox.setLocale(new Locale("en"));
        this.label.setWidth("100%");
        this.label.setParent(this);// Default show a label with text
        this.editable = false;
        initEditCtrl();
    }
 
    public IREditableDoublebox(Double num) {
        this();
        setNumber(num);
    }
    
    public IREditableDoublebox(Double num, String format, Constraint constraint) {
        this();
        this.doublebox.setFormat(format);
        setNumber(num);
        if (constraint != null) this.doublebox.setConstraint(constraint);
    }
 
    public IREditableDoublebox(Double num, String format) {
    	 this();
    	 this.doublebox.setConstraint((Constraint)null);
    	 this.doublebox.setFormat(format);
         setNumber(num);
	}

	// Getter and setters
    public void setNumber(Double num) {
        this.num = num;
        Constraint constraint= this.doublebox.getConstraint();
        this.doublebox.setConstraint("");
        this.doublebox.setValue(num);
        this.doublebox.setConstraint(constraint);
        if (num != null)
        	this.label.setValue(numberFormat(num));
        else
        	this.label.setValue("");
    }
    
    public Doublebox getDoublebox(){
    	return this.doublebox;
    }
    
    public Double getNumber() {
        return this.num;
    }
    
    public Double getValue(){
    	return this.doublebox.getValue();
    }
    
    public void setMaxLength(Integer maxlength){
    	this.doublebox.setMaxlength(maxlength);
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
    		this.num = this.doublebox.getValue();
    		if (this.num != null)
    			this.label.setValue(numberFormat(this.num));
    		else
    			this.label.setValue("");
        } else {
        	Constraint constraint = this.doublebox.getConstraint();
        	this.doublebox.setConstraint((Constraint)null);
        	this.doublebox.setValue(this.num);
        	this.doublebox.setConstraint(constraint);
        	if (this.num != null)
    			this.label.setValue(numberFormat(this.num));
    		else
    			this.label.setValue("");
        }
    }
    
    private void toggleEditable() {
    	this.editable = !this.editable;
    	if (this.editable) {
            this.label.detach();
            this.appendChild(this.doublebox);
        } else {
            this.doublebox.detach();
            this.appendChild(this.label);
        } 
    }
    
    private String numberFormat(Double num){
    	String format = this.doublebox.getFormat();
    	if(format != null &&  !format.isEmpty()){
			return NumberFormatUtil.format(num, format);
    	}
    	return NumberFormatUtil.format(this.num, 2);
    }
    
}
