package com.seidor.inventario.inroweditablecomps;

import java.util.Date;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;

import com.seidor.inventario.util.DateFormatUtil;

public class IREditableCheckbox extends Div {
	
	private static final long serialVersionUID = 1L;
	public static final String ON_EDITABLE = "onEditable";
	public static final String ON_UPDATE = "onUpdate";
	
	private Boolean flag = null;
	private Date date;
	private Boolean hourB;
	private Boolean dateB;
	private Boolean editable;
	private Checkbox checkbox;
	private Label label;
	
	public IREditableCheckbox() {
		this.checkbox = new Checkbox();
		this.label = new Label();
		this.label.setWidth("99%");
		this.label.setParent(this);// Default show a label with text
		this.editable = false;
		this.setStyle("text-align: center;");
		initEditCtrl();
		
		//Date checkbox event
		this.checkbox.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Checkbox cb = (Checkbox)event.getTarget();
				IREditableCheckbox irecb = (IREditableCheckbox)cb.getParent();
				if (irecb != null) {
					if (cb.isChecked()) {
						irecb.setDate(new Date());
					}
					else {
						irecb.setDate(null);
					}
				}
			}
		});
	}
 
	public IREditableCheckbox(Boolean flag) {
		this();
		setFlag(flag);
	}
	
	public IREditableCheckbox(Boolean flag, String labelClass) {
		this();
		setFlag(flag);
		this.label.setSclass(labelClass);
	}
	
	public IREditableCheckbox(Date date, String labelClass, Boolean dateB, Boolean hourB) {
		this();
		this.dateB = dateB;
		this.hourB = hourB;
		this.setDate(date);
		this.label.setSclass(labelClass);
	}
	
	// Getter and setters
	public void setFlag(Boolean flag) {
		this.flag = flag;
		if (flag != null) this.checkbox.setChecked(flag);
		this.label.setValue(this.getLabelValue(flag));
	}
	
	public Boolean getFlag() {
		return this.flag;
	}
	
	public void setDisabled(Boolean disabled) {
		this.checkbox.setDisabled(disabled);
	}
	
	public boolean getValue(){
		return this.checkbox.isChecked();
	}
	
	public Checkbox getCheckbox(){
		return this.checkbox;
	}
	
	public void setDate(Date date) {
		this.date = date;
		if (this.date != null) this.flag = true;
		else this.flag = false;
		if (Boolean.TRUE.equals(this.dateB) && Boolean.TRUE.equals(this.hourB))
			this.label.setValue(DateFormatUtil.getFormatedDate(this.date, false) + " " + DateFormatUtil.getHour(date));
		else if (Boolean.TRUE.equals(this.dateB))
			this.label.setValue(DateFormatUtil.getFormatedDate(this.date, false));
		else if (Boolean.TRUE.equals(this.hourB))
			this.label.setValue(DateFormatUtil.getHour(this.date));
	}
 
	public Date getDate() {
		return this.date;
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
		if (Boolean.TRUE.equals(this.dateB) || Boolean.TRUE.equals(this.hourB)) {
			if (applyChange) {
				if (Boolean.TRUE.equals(this.dateB) && Boolean.TRUE.equals(this.hourB))
					this.label.setValue(DateFormatUtil.getFormatedDate(this.date, false) + " " + DateFormatUtil.getHour(date));
				else if (Boolean.TRUE.equals(this.dateB))
					this.label.setValue(DateFormatUtil.getFormatedDate(this.date, false));
				else if (Boolean.TRUE.equals(this.hourB))
					this.label.setValue(DateFormatUtil.getHour(this.date));
			} else {
				if (this.flag != null) this.checkbox.setChecked(this.flag);
				if (Boolean.TRUE.equals(this.dateB) && Boolean.TRUE.equals(this.hourB))
					this.label.setValue(DateFormatUtil.getFormatedDate(this.date, false) + " " + DateFormatUtil.getHour(date));
				else if (Boolean.TRUE.equals(this.dateB))
					this.label.setValue(DateFormatUtil.getFormatedDate(this.date, false));
				else if (Boolean.TRUE.equals(this.hourB))
					this.label.setValue(DateFormatUtil.getHour(this.date));
			}
		} else {
			if (applyChange) {
				this.flag = this.checkbox.isChecked();
				this.label.setValue(this.getLabelValue(this.flag));
			} else {
				if (this.flag != null) this.checkbox.setChecked(this.flag);
				this.label.setValue(this.getLabelValue(this.flag));
			}
		}
	}
	
	private void toggleEditable() {
		this.editable = !this.editable;
		if (this.editable) {
			this.label.detach();
			this.appendChild(this.checkbox);
		} else {
			this.checkbox.detach();
			this.appendChild(this.label);
		} 
	}
	
	private String getLabelValue(Boolean flag){
		if (flag != null && flag){
			return "Sí";
		}
		return "No"; 
	}
	
}