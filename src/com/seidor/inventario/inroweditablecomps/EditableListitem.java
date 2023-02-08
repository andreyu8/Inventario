package com.seidor.inventario.inroweditablecomps;

import com.seidor.inventario.navigation.Action;
import com.seidor.inventario.validation.FullComponentValidator;

import java.util.Iterator;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Vlayout;


public class EditableListitem extends Listitem {

	private static final long serialVersionUID = 1L;
	public static final String ON_EDIT = "onEdit";
	public static final String ON_UPDATE = "onUpdate";
	
	private boolean editable = false;
 
	public EditableListitem() {
		this.addEventListener(ON_EDIT, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				toggleEditable();
			}
		});
   
		this.addEventListener(ON_UPDATE, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Boolean applyChange = (Boolean) event.getData();
				endEditing(applyChange);
			}
		});
	}
 
	public boolean isEditable() {
		return editable;
	}
 
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	
	public void toggleEditable() {
		setEditable(!this.editable);
		for (Iterator<Component> it = (Iterator<Component>)this.getChildren().iterator(); it.hasNext();) {
			Component editable = it.next();
			toggleEditableRecursive(editable);
		}
	}
	
	public void toggleEditableRecursive(Component comp) {
		for (Iterator<Component> it = (Iterator<Component>)comp.getChildren().iterator(); it.hasNext();) {
			Component editable = it.next();
			if(editable instanceof Vlayout){
				toggleEditableRecursive(editable);
			}else if(editable instanceof Hlayout){
				toggleEditableRecursive(editable);
			}else{
				convertEditable(editable);
			}
		}		
	}
	
	public void convertEditable(Component editable){
		if (editable != null){
			if (editable instanceof IREditableTextbox) {
				Events.postEvent(new Event(IREditableTextbox.ON_EDITABLE, editable, null));
			}
			else if (editable instanceof IREditableIntbox) {
				Events.postEvent(new Event(IREditableIntbox.ON_EDITABLE, editable, null));
			}
			else if (editable instanceof IREditableDoublebox) {
				Events.postEvent(new Event(IREditableDoublebox.ON_EDITABLE, editable, null));
			}
			else if (editable instanceof IREditableDatebox) {
				Events.postEvent(new Event(IREditableDatebox.ON_EDITABLE, editable, null));
			}
			else if (editable instanceof IREditableCombobox) {
				Events.postEvent(new Event(IREditableCombobox.ON_EDITABLE, editable, null));
			}
			else if (editable instanceof IREditableBandbox) {
				Events.postEvent(new Event(IREditableBandbox.ON_EDITABLE, editable, null));
			}
			else if (editable instanceof IREditableCheckbox) {
				Events.postEvent(new Event(IREditableCheckbox.ON_EDITABLE, editable, null));
			}
			else if (editable instanceof IREditablePassbox) {
				Events.postEvent(new Event(IREditablePassbox.ON_EDITABLE, editable, null));
			}
			else if (editable instanceof IREditableComponent) {
				Events.postEvent(new Event(((IREditableComponent)editable).onEditable(), editable, null));
			}
		}
	}
	
	public void endEditing(Boolean applyChanges) {
		if (applyChanges) {	
			FullComponentValidator fcv = new FullComponentValidator();
			fcv.validate(EditableListitem.this);
			
			Action action = (Action)EditableListitem.this.getAttribute("action");
			if (action != null) action.execute();
		}
		else {
			Action action = (Action)EditableListitem.this.getAttribute("cancelAction");
			if (action != null) action.execute();
		}
		
		setEditable(!this.editable);
		for(Iterator<Component> it = (Iterator<Component>)this.getChildren().iterator(); it.hasNext();) {
			Component editable = it.next();
			toggleEndEditingRecursive(editable, applyChanges);
		}
	}
		
	public void toggleEndEditingRecursive(Component comp, Boolean applyChanges) {
		for (Iterator<Component> it = (Iterator<Component>)comp.getChildren().iterator(); it.hasNext();) {
			Component editable = it.next();
			if(editable instanceof Vlayout){
				toggleEndEditingRecursive(editable, applyChanges);
			}else if(editable instanceof Hlayout){
				toggleEndEditingRecursive(editable, applyChanges);
			}else{
				convertEndEditing(editable, applyChanges);
			}
		}		
	}
	
	public void convertEndEditing(Component editable, Boolean applyChanges){
		if (editable != null){
			if (editable instanceof IREditableTextbox) {
				Events.postEvent(new Event(IREditableTextbox.ON_UPDATE, editable, applyChanges));
			}
			else if (editable instanceof IREditableIntbox) {
				Events.postEvent(new Event(IREditableIntbox.ON_UPDATE, editable, applyChanges));
			}
			else if (editable instanceof IREditableDoublebox) {
				Events.postEvent(new Event(IREditableDoublebox.ON_UPDATE, editable, applyChanges));
			}
			else if (editable instanceof IREditableDatebox) {
				Events.postEvent(new Event(IREditableDatebox.ON_UPDATE, editable, applyChanges));
			}
			else if (editable instanceof IREditableCombobox) {
				Events.postEvent(new Event(IREditableCombobox.ON_UPDATE, editable, applyChanges));
			}
			else if (editable instanceof IREditableBandbox) {
				Events.postEvent(new Event(IREditableBandbox.ON_UPDATE, editable, applyChanges));
			}
			else if (editable instanceof IREditableCheckbox) {
				Events.postEvent(new Event(IREditableCheckbox.ON_UPDATE, editable, applyChanges));
			}
			else if (editable instanceof IREditablePassbox) {
				Events.postEvent(new Event(IREditablePassbox.ON_UPDATE, editable, applyChanges));
			}
			else if (editable instanceof IREditableComponent) {
				Events.postEvent(new Event(((IREditableComponent)editable).onUpdate(), editable, applyChanges));
			}
		}
	}
	
}
