package com.seidor.inventario.inroweditablecomps;

import com.seidor.inventario.actionimages.Accept;
import com.seidor.inventario.actionimages.Cancel;
import com.seidor.inventario.actionimages.Delete;
import com.seidor.inventario.actionimages.Download;
import com.seidor.inventario.actionimages.GeoLocation;
import com.seidor.inventario.actionimages.Modify;
import com.seidor.inventario.actionimages.UploadButton;
import com.seidor.inventario.navigation.Action;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.ListitemRendererExt;


public class EditableListitemRenderer implements ListitemRenderer<Object>, ListitemRendererExt {
	
	public Listitem newListitem(Listbox listbox) {
		// Create EditableListitem instead of Listitem(default)
		EditableListitem editListitem = new EditableListitem();
		editListitem.applyProperties();
		return editListitem;
	}
	
	public Listcell newListcell(Listitem listitem) {
		Listcell listcell = new Listcell();
		listcell.setParent(listitem);
		return listcell;
	}
	
	public int getControls() {
		return ListitemRendererExt.DETACH_ON_RENDER;
	}
	
	public void render(Listitem listitem, Object data, int index) throws Exception {
		
	}
	
	protected void createControlButtons(EditableListitem editListitem, Action action, EventListener<Event> deleteEvent, Boolean editing){
		final Div ctrlDiv = new Div();
		Listcell listcell = this.newListcell(editListitem);
		listcell.setClass("removeOnPrint");
		ctrlDiv.setParent(listcell);
		editListitem.setAttribute("action", action);
		
		final Modify modifyImage = new Modify();
		modifyImage.setWidth("20px");
		editListitem.setAttribute("modify", modifyImage);
		
		final Delete deleteImage = new Delete();
		deleteImage.setWidth("20px");
		editListitem.setAttribute("delete", deleteImage);

		final Accept acceptImage = new Accept();
		acceptImage.setWidth("20px");
		editListitem.setAttribute("accept", acceptImage);

		final Cancel cancelImage = new Cancel();
		cancelImage.setWidth("20px");
		editListitem.setAttribute("cancel", cancelImage);

		final Delete dropImage = new Delete();
		dropImage.setWidth("20px");
		
		//Button listener - control the editable of row
		modifyImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				cancelImage.setParent(ctrlDiv);
				acceptImage.setParent(ctrlDiv);
				EditableListitem editListitem = (EditableListitem)acceptImage.getParent().getParent().getParent();
				editListitem.toggleEditable();
				modifyImage.detach();
				deleteImage.detach();
			}
		});
		
		deleteImage.addEventListener(Events.ON_CLICK, deleteEvent);
		
		acceptImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				EditableListitem editListitem = (EditableListitem)acceptImage.getParent().getParent().getParent();
				editListitem.endEditing(true);
				cancelImage.detach();
				acceptImage.detach();
				dropImage.detach();
				modifyImage.setParent(ctrlDiv);
				deleteImage.setParent(ctrlDiv);
			}
		});
		
		cancelImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				EditableListitem editListitem = (EditableListitem)cancelImage.getParent().getParent().getParent();
				editListitem.endEditing(false);
				cancelImage.detach();
				acceptImage.detach();
				modifyImage.setParent(ctrlDiv);
				deleteImage.setParent(ctrlDiv);
			}
		});
		
		dropImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				EditableListitem editListitem = (EditableListitem)dropImage.getParent().getParent().getParent();
				Listbox listbox = editListitem.getListbox();
				if (listbox != null) {
					ListModelList<Object> model = (ListModelList<Object>)listbox.getModel();
					model.remove(listbox.getIndexOfItem(editListitem));
				}
			}
		});
		
		if (editing){
			acceptImage.setParent(ctrlDiv);
			dropImage.setParent(ctrlDiv);
			editListitem.toggleEditable();
		}
		else {
			modifyImage.setParent(ctrlDiv);
			deleteImage.setParent(ctrlDiv);
		}
	}
	
	protected void createControlButtonsFileControls(EditableListitem editListitem, Action action, EventListener<Event> deleteEvent, EventListener<Event> downloadEvent, EventListener<UploadEvent> uploadEvent, Boolean editing){
		final Div ctrlDiv = new Div();
		Listcell listcell = this.newListcell(editListitem);
		listcell.setClass("removeOnPrint");
		ctrlDiv.setParent(listcell);
		editListitem.setAttribute("action", action);
		
		final Modify modifyImage = new Modify();
		modifyImage.setWidth("20px");
		
		final Delete deleteImage = new Delete();
		deleteImage.setWidth("20px");
		
		final Accept acceptImage = new Accept();
		acceptImage.setWidth("20px");
		
		final Cancel cancelImage = new Cancel();
		cancelImage.setWidth("20px");
		
		final Delete dropImage = new Delete();
		dropImage.setWidth("20px");
		
		final Download download = new Download();
		download.setWidth("20px");
		
		final UploadButton upload = new UploadButton();
		upload.setWidth("30px");
		
		upload.addEventListener(Events.ON_UPLOAD, uploadEvent);
		download.addEventListener(Events.ON_CLICK, downloadEvent);
		
		//Button listener - control the editable of row
		modifyImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				upload.setParent(ctrlDiv);
				cancelImage.setParent(ctrlDiv);
				acceptImage.setParent(ctrlDiv);
				EditableListitem editListitem = (EditableListitem)acceptImage.getParent().getParent().getParent();
				editListitem.toggleEditable();
				download.detach();
				modifyImage.detach();
				deleteImage.detach();
			}
		});
		
		deleteImage.addEventListener(Events.ON_CLICK, deleteEvent);
		
		acceptImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				EditableListitem editListitem = (EditableListitem)acceptImage.getParent().getParent().getParent();
				editListitem.endEditing(true);
				cancelImage.detach();
				acceptImage.detach();
				dropImage.detach();
				upload.detach();
				download.setParent(ctrlDiv);
				modifyImage.setParent(ctrlDiv);
				deleteImage.setParent(ctrlDiv);
				
			}
		});
		
		cancelImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				EditableListitem editListitem = (EditableListitem)cancelImage.getParent().getParent().getParent();
				editListitem.endEditing(false);
				cancelImage.detach();
				acceptImage.detach();
				upload.detach();
				download.setParent(ctrlDiv);
				modifyImage.setParent(ctrlDiv);
				deleteImage.setParent(ctrlDiv);
				
			}
		});
		
		dropImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				EditableListitem editListitem = (EditableListitem)dropImage.getParent().getParent().getParent();
				Listbox listbox = editListitem.getListbox();
				if (listbox != null) {
					ListModelList<Object> model = (ListModelList<Object>)listbox.getModel();
					model.remove(listbox.getIndexOfItem(editListitem));
				}
			}
		});
		
		if (editing){
			upload.setParent(ctrlDiv);
			acceptImage.setParent(ctrlDiv);
			dropImage.setParent(ctrlDiv);
			
			editListitem.toggleEditable();
		}
		else {
			download.setParent(ctrlDiv);
			modifyImage.setParent(ctrlDiv);
			deleteImage.setParent(ctrlDiv);
			
		}
	}
	
	protected void createControlButtonsGeoLocationControls(EditableListitem editListitem, Action action, EventListener<Event> deleteEvent, Boolean editing, EventListener<Event> locationEvent){
		final Div ctrlDiv = new Div();
		Listcell listcell = this.newListcell(editListitem);
		listcell.setClass("removeOnPrint");
		ctrlDiv.setParent(listcell);
		editListitem.setAttribute("action", action);
		
		final Modify modifyImage = new Modify();
		modifyImage.setWidth("20px");
		
		final Delete deleteImage = new Delete();
		deleteImage.setWidth("20px");
		
		final Accept acceptImage = new Accept();
		acceptImage.setWidth("20px");
		
		final Cancel cancelImage = new Cancel();
		cancelImage.setWidth("20px");
		
		final Delete dropImage = new Delete();
		dropImage.setWidth("20px");
		
		final GeoLocation geoLocation = new GeoLocation();
		
		geoLocation.addEventListener(Events.ON_CLICK, locationEvent);
		
		//Button listener - control the editable of row
		modifyImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				cancelImage.setParent(ctrlDiv);
				acceptImage.setParent(ctrlDiv);
				EditableListitem editListitem = (EditableListitem)acceptImage.getParent().getParent().getParent();
				editListitem.toggleEditable();
				modifyImage.detach();
				deleteImage.detach();
			}
		});
		
		deleteImage.addEventListener(Events.ON_CLICK, deleteEvent);
		
		acceptImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				EditableListitem editListitem = (EditableListitem)acceptImage.getParent().getParent().getParent();
				editListitem.endEditing(true);
				cancelImage.detach();
				acceptImage.detach();
				dropImage.detach();
				modifyImage.setParent(ctrlDiv);
				deleteImage.setParent(ctrlDiv);
			}
		});
		
		cancelImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				EditableListitem editListitem = (EditableListitem)cancelImage.getParent().getParent().getParent();
				editListitem.endEditing(false);
				cancelImage.detach();
				acceptImage.detach();
				modifyImage.setParent(ctrlDiv);
				deleteImage.setParent(ctrlDiv);
			}
		});
		
		dropImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				EditableListitem editListitem = (EditableListitem)dropImage.getParent().getParent().getParent();
				Listbox listbox = editListitem.getListbox();
				if (listbox != null) {
					ListModelList<Object> model = (ListModelList<Object>)listbox.getModel();
					model.remove(listbox.getIndexOfItem(editListitem));
				}
			}
		});
		
		geoLocation.setParent(ctrlDiv);
		if (editing){
			acceptImage.setParent(ctrlDiv);
			dropImage.setParent(ctrlDiv);
			editListitem.toggleEditable();
		}
		else {
			modifyImage.setParent(ctrlDiv);
			deleteImage.setParent(ctrlDiv);
		}
	}
	
	protected void createControlButtons(EditableListitem editListitem, Action action, Action cancelAction, EventListener<Event> deleteEvent, Boolean editing){
		final Div ctrlDiv = new Div();
		Listcell listcell = this.newListcell(editListitem);
		listcell.setClass("removeOnPrint");
		ctrlDiv.setParent(listcell);
		editListitem.setAttribute("action", action);
		editListitem.setAttribute("cancelAction", cancelAction);
		
		final Modify modifyImage = new Modify();
		modifyImage.setWidth("20px");
		
		final Delete deleteImage = new Delete();
		deleteImage.setWidth("20px");
		
		final Accept acceptImage = new Accept();
		acceptImage.setWidth("20px");
		
		final Cancel cancelImage = new Cancel();
		cancelImage.setWidth("20px");
		
		final Delete dropImage = new Delete();
		dropImage.setWidth("20px");
		
		//Button listener - control the editable of row
		modifyImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				cancelImage.setParent(ctrlDiv);
				acceptImage.setParent(ctrlDiv);
				EditableListitem editListitem = (EditableListitem)acceptImage.getParent().getParent().getParent();
				editListitem.toggleEditable();
				modifyImage.detach();
				deleteImage.detach();
			}
		});
		
		deleteImage.addEventListener(Events.ON_CLICK, deleteEvent);
		
		acceptImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				EditableListitem editListitem = (EditableListitem)acceptImage.getParent().getParent().getParent();
				editListitem.endEditing(true);
				cancelImage.detach();
				acceptImage.detach();
				dropImage.detach();
				modifyImage.setParent(ctrlDiv);
				deleteImage.setParent(ctrlDiv);
			}
		});
		
		cancelImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				EditableListitem editListitem = (EditableListitem)cancelImage.getParent().getParent().getParent();
				editListitem.endEditing(false);
				cancelImage.detach();
				acceptImage.detach();
				modifyImage.setParent(ctrlDiv);
				deleteImage.setParent(ctrlDiv);
			}
		});
		
		dropImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				try {
					EditableListitem editListitem = (EditableListitem)dropImage.getParent().getParent().getParent();
					Listbox listbox = editListitem.getListbox();
					if (listbox != null) {
						ListModelList<Object> model = (ListModelList<Object>)listbox.getModel();
						model.remove(listbox.getIndexOfItem(editListitem));
					}
				} catch (Exception ex) { }
			}
		});
		
		if (editing){
			acceptImage.setParent(ctrlDiv);
			dropImage.setParent(ctrlDiv);
			editListitem.toggleEditable();
		}
		else {
			modifyImage.setParent(ctrlDiv);
			deleteImage.setParent(ctrlDiv);
		}
	}
	
	protected void createControlButtons(EditableListitem editListitem, EventListener<Event> deleteEvent){
		final Div ctrlDiv = new Div();
		Listcell listcell = this.newListcell(editListitem);
		listcell.setClass("removeOnPrint");
		ctrlDiv.setParent(listcell);
		
		final Delete deleteImage = new Delete();
		deleteImage.setWidth("20px");
		deleteImage.addEventListener(Events.ON_CLICK, deleteEvent);
		deleteImage.setParent(ctrlDiv);
	}
	
	protected void createControlButtonsWithDownload(EditableListitem editListitem, EventListener<Event> deleteEvent, EventListener<Event> modifyEvent, EventListener<Event> downloadEvent ){
		final Div ctrlDiv = new Div();
		Listcell listcell = this.newListcell(editListitem);
		listcell.setClass("removeOnPrint");
		ctrlDiv.setParent(listcell);
		
		final Download downloadImage = new Download();
		downloadImage.setWidth("20px");
		downloadImage.addEventListener(Events.ON_CLICK, downloadEvent);
		downloadImage.setParent(ctrlDiv);

		final Modify modifyImage = new Modify();
		modifyImage.setWidth("20px");
		modifyImage.addEventListener(Events.ON_CLICK, modifyEvent);
		modifyImage.setParent(ctrlDiv); 
		
		final Delete deleteImage = new Delete();
		deleteImage.setWidth("20px");
		deleteImage.addEventListener(Events.ON_CLICK, deleteEvent);
		deleteImage.setParent(ctrlDiv);
	}
	
	protected void createControlButtonsWithoutModify(EditableListitem editListitem, EventListener<Event> deleteEvent, EventListener<Event> downloadEvent ){
		final Div ctrlDiv = new Div();
		Listcell listcell = this.newListcell(editListitem);
		listcell.setClass("removeOnPrint");
		ctrlDiv.setParent(listcell);
		
		if (downloadEvent != null) {
			final Download downloadImage = new Download();
			downloadImage.setWidth("20px");
			downloadImage.addEventListener(Events.ON_CLICK, downloadEvent);
			downloadImage.setParent(ctrlDiv);
		}
		else {
			final Download downloadImage = new Download();
			downloadImage.setWidth("20px");
			
			
			downloadEvent = new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					
				}
			};
			downloadImage.addEventListener(Events.ON_CLICK, downloadEvent);
			downloadImage.setParent(ctrlDiv);
		}
		
		final Delete deleteImage = new Delete();
		deleteImage.setWidth("20px");
		deleteImage.addEventListener(Events.ON_CLICK, deleteEvent);
		deleteImage.setParent(ctrlDiv);
	}
	
	protected void createControlButtonsWhitoutDelete(EditableListitem editListitem, Action action, Boolean editing){
		final Div ctrlDiv = new Div();
		Listcell listcell = this.newListcell(editListitem);
		listcell.setClass("removeOnPrint");
		ctrlDiv.setParent(listcell);
		editListitem.setAttribute("action", action);
		
		final Modify modifyImage = new Modify();
		modifyImage.setWidth("20px");
		
		final Accept acceptImage = new Accept();
		acceptImage.setWidth("20px");
		
		final Cancel cancelImage = new Cancel();
		cancelImage.setWidth("20px");
		
		//Button listener - control the editable of row
		modifyImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				cancelImage.setParent(ctrlDiv);
				acceptImage.setParent(ctrlDiv);
				EditableListitem editListitem = (EditableListitem)acceptImage.getParent().getParent().getParent();
				editListitem.toggleEditable();
				modifyImage.detach();
			}
		});
		
		acceptImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				EditableListitem editListitem = (EditableListitem)acceptImage.getParent().getParent().getParent();
				editListitem.endEditing(true);
				cancelImage.detach();
				acceptImage.detach();
				modifyImage.setParent(ctrlDiv);
			}
		});
		
		cancelImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				EditableListitem editListitem = (EditableListitem)cancelImage.getParent().getParent().getParent();
				editListitem.endEditing(false);
				cancelImage.detach();
				acceptImage.detach();
				modifyImage.setParent(ctrlDiv);
			}
		});
		
		if (editing){
			acceptImage.setParent(ctrlDiv);
			editListitem.toggleEditable();
		}
		else {
			modifyImage.setParent(ctrlDiv);
		}
	}
	
	protected void createControlButtonsWithoutDrop(EditableListitem editListitem, Action action, EventListener<Event> deleteEvent, Boolean editing){
		final Div ctrlDiv = new Div();
		Listcell listcell = this.newListcell(editListitem);
		listcell.setClass("removeOnPrint");
		ctrlDiv.setParent(listcell);
		editListitem.setAttribute("action", action);
		
		final Modify modifyImage = new Modify();
		modifyImage.setWidth("20px");
		
		final Delete deleteImage = new Delete();
		deleteImage.setWidth("20px");
		
		final Accept acceptImage = new Accept();
		acceptImage.setWidth("20px");
		
		final Cancel cancelImage = new Cancel();
		cancelImage.setWidth("20px");
		
		//Button listener - control the editable of row
		modifyImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				cancelImage.setParent(ctrlDiv);
				acceptImage.setParent(ctrlDiv);
				EditableListitem editListitem = (EditableListitem)acceptImage.getParent().getParent().getParent();
				editListitem.toggleEditable();
				modifyImage.detach();
				deleteImage.detach();
			}
		});
		
		deleteImage.addEventListener(Events.ON_CLICK, deleteEvent);
		
		acceptImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				EditableListitem editListitem = (EditableListitem)acceptImage.getParent().getParent().getParent();
				editListitem.endEditing(true);
				cancelImage.detach();
				acceptImage.detach();
				modifyImage.setParent(ctrlDiv);
				deleteImage.setParent(ctrlDiv);
			}
		});
		
		cancelImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				EditableListitem editListitem = (EditableListitem)cancelImage.getParent().getParent().getParent();
				editListitem.endEditing(false);
				cancelImage.detach();
				acceptImage.detach();
				modifyImage.setParent(ctrlDiv);
				deleteImage.setParent(ctrlDiv);
			}
		});
		
		if (editing){
			acceptImage.setParent(ctrlDiv);
			editListitem.toggleEditable();
		}
		else {
			modifyImage.setParent(ctrlDiv);
			deleteImage.setParent(ctrlDiv);
		}
	}
	
	protected void createControlButtonsOnlySave(EditableListitem editListitem, Action action, Boolean editing){
		final Div ctrlDiv = new Div();
		Listcell listcell = this.newListcell(editListitem);
		listcell.setClass("removeOnPrint");
		ctrlDiv.setParent(listcell);
		editListitem.setAttribute("action", action);
		
		final Accept acceptImage = new Accept();
		acceptImage.setWidth("20px");
		
		final Delete dropImage = new Delete();
		dropImage.setWidth("20px");
		
		//Button listener - control the editable of row
		acceptImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				EditableListitem editListitem = (EditableListitem)acceptImage.getParent().getParent().getParent();
				editListitem.endEditing(true);
				acceptImage.detach();
				dropImage.detach();
			}
		});
		
		dropImage.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				try {
					EditableListitem editListitem = (EditableListitem)dropImage.getParent().getParent().getParent();
					Listbox listbox = editListitem.getListbox();
					if (listbox != null) {
						ListModelList<Object> model = (ListModelList<Object>)listbox.getModel();
						model.remove(listbox.getIndexOfItem(editListitem));
					}
				} catch (Exception ex) { }
			}
		});
		
		if (editing){
			acceptImage.setParent(ctrlDiv);
			dropImage.setParent(ctrlDiv);
			editListitem.toggleEditable();
		}
	}
	
}