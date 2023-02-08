package com.seidor.inventario.inroweditablecomps;

import java.text.DateFormat;
import java.util.Date;

import org.zkoss.text.DateFormats;
import org.zkoss.util.Locales;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;

import com.seidor.inventario.util.DateFormatUtil;


public class IREditableDatebox extends Div {
	
	private static final long serialVersionUID = 1L;
	public static final String ON_EDITABLE = "onEditable";
	public static final String ON_UPDATE = "onUpdate";
	
	private Date date;
	private Boolean editable;
	private Boolean hour;
	private Datebox datebox;
	private Label label;
	
	public IREditableDatebox() {
		this.datebox = new Datebox();
		this.datebox.setZclass("gs-datebox");
		this.label = new Label();
		this.datebox.setWidth("100%");
		this.label.setWidth("100%");
		this.label.setParent(this);// Default show a label with text
		this.editable = false;
		this.hour = false;
		initEditCtrl();
	}
 
	public IREditableDatebox(Date date) {
		this();
		setDate(date);
	}
	
	public IREditableDatebox(Date date, Constraint constraint) {
		this();
		setDate(date);
		this.datebox.setConstraint(constraint);
	}
	
	public IREditableDatebox(Date date, Constraint constraint, Boolean hour) {
		this();
		this.hour = hour;
		if (this.hour != null && this.hour) 
			this.datebox.setFormat(DateFormats.getDateFormat(DateFormat.MEDIUM, Locales.getCurrent(), null) + " kk:mm");
		
		this.setDate(date);
		this.datebox.setConstraint(constraint);
	}
	
	public IREditableDatebox(Date date, Constraint constraint, Boolean hour, String labelClass) {
		this();
		this.hour = hour;
		if (this.hour != null && this.hour) this.datebox.setFormat(DateFormats.getDateFormat(DateFormat.MEDIUM, Locales.getCurrent(), null) + " kk:mm");
		this.setDate(date);
		this.datebox.setConstraint(constraint);
		this.label.setSclass(labelClass);
	}
	
	public IREditableDatebox(Date date, Constraint constraint, String format) {
		this();
		this.hour = false;
		try { this.datebox.setFormat(format); } catch (Exception e) { }
		this.setDate(date);
		this.datebox.setConstraint(constraint);
	}
	
	
	// Getter and setters
	public Datebox getDatebox(){
		return this.datebox;
	}
	
	public void setDate(Date date) {
		this.date = date;
		this.datebox.setValue(date);
		if (this.hour != null && this.hour)
			this.label.setValue(DateFormatUtil.getFormatedDate(date, false) + " " + DateFormatUtil.getHour(date));
		else
			this.label.setValue(DateFormatUtil.getFormatedDate(date, false));
	}
 
	public Date getDate() {
		return this.date;
	}
	
	public Date getValue(){
		return this.datebox.getValue();
	}
 
	public Boolean getHour() {
		return hour;
	}

	public void setHour(Boolean hour) {
		this.hour = hour;
		if (this.hour != null && this.hour) this.datebox.setFormat(DateFormats.getDateFormat(DateFormat.MEDIUM, Locales.getCurrent(), null) + " kk:mm");
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
			this.date = this.datebox.getValue();
			if (this.hour != null && this.hour)
				this.label.setValue(DateFormatUtil.getFormatedDate(date, false) + " " + DateFormatUtil.getHour(date));
			else
				this.label.setValue(DateFormatUtil.getFormatedDate(date, false));
		} else {
			Constraint constraint = this.datebox.getConstraint();
			this.datebox.setConstraint((Constraint)null);
			this.datebox.setValue(this.date);
			this.datebox.setConstraint(constraint);
			if (this.hour != null && this.hour)
				this.label.setValue(DateFormatUtil.getFormatedDate(date, false) + " " + DateFormatUtil.getHour(date));
			else
				this.label.setValue(DateFormatUtil.getFormatedDate(date, false));
		}
	}
	
	private void toggleEditable() {
		this.editable = !this.editable;
		if (this.editable) {
			this.label.detach();
			this.appendChild(this.datebox);
		} else {
			this.datebox.detach();
			this.appendChild(this.label);
		} 
	}
	
	public Label getLabel(){
		return this.label;
		
	}
	
}
