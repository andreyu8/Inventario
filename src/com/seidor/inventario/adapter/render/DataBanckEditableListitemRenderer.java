package com.seidor.inventario.adapter.render;

import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.A;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.seidor.inventario.adapter.listitem.DataBankListitemAdapter;
import com.seidor.inventario.adapter.listitem.ProductListitemAdapter;
import com.seidor.inventario.controller.ProviderController;
import com.seidor.inventario.controller.PurchaseOrderController;
import com.seidor.inventario.inroweditablecomps.EditableListitem;
import com.seidor.inventario.inroweditablecomps.EditableListitemRenderer;
import com.seidor.inventario.inroweditablecomps.IREditableCombobox;
import com.seidor.inventario.inroweditablecomps.IREditableDoublebox;
import com.seidor.inventario.inroweditablecomps.IREditableIntbox;
import com.seidor.inventario.inroweditablecomps.IREditableTextbox;
import com.seidor.inventario.manager.ProductManager;
import com.seidor.inventario.model.DatosBancarios;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.navigation.Action;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.validation.*;

public class DataBanckEditableListitemRenderer  extends EditableListitemRenderer {

	
	public DataBanckEditableListitemRenderer() {
		super();
	}
	
	
	@Override
	public void render(Listitem listitem, Object data, int index) throws Exception {
		DataBankListitemAdapter dblia = (DataBankListitemAdapter)data;
		DatosBancarios datosBancarios= dblia.getDatosBancarios();
		final EditableListitem editListitem = (EditableListitem) listitem;
		
		
		IREditableTextbox banco= new IREditableTextbox((datosBancarios.getBanco()));
		banco.setParent(this.newListcell(editListitem));
		
		IREditableTextbox cta= new IREditableTextbox((datosBancarios.getCta()));
		cta.setParent(this.newListcell(editListitem));
		
		IREditableTextbox clabe= new IREditableTextbox((datosBancarios.getClabe()), new BigintboxMandatoryValidator(), 18);
		clabe.setParent(this.newListcell(editListitem));
		
		
		Action action = new Action() {
			public void execute() {
				ProviderController controller = (ProviderController)SpringUtil.getBean("providerController");
				controller.recordCtaBancaria(editListitem);
			}
		};
		
		EventListener<Event> deleteEvent = new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Listitem li = (Listitem)event.getTarget().getParent().getParent().getParent();
				Listbox lb = li.getListbox();
				lb.setSelectedItem(li);
				Action deleteAction = new Action() {
					public void execute(){
						ProviderController controller = (ProviderController)SpringUtil.getBean("providerController");
						controller.deleteCtaBancaria(editListitem);
						lb.removeItemAt(li.getIndex());
					}
				};
				NavigationControl.openModalWindowWithMessage("¿Realmente desea borrar el producto seleccionado?", deleteAction);
			}
		};
		
		if (datosBancarios.getIdDatosBancarios() == null) {
			this.createControlButtons(editListitem, action, deleteEvent, true);
		} else {
			this.createControlButtons(editListitem, action, deleteEvent, false);
		}
		
		
	}
	

	private void addCatPrecio(Intbox catidad, Doublebox precio, Doublebox subtotal) {
		precio.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Doublebox target = (Doublebox)event.getTarget();
				Listitem li = (Listitem)target.getParent().getParent().getParent();
				
				System.out.println(catidad.getValue());
				System.out.println(precio.getValue());
				
				subtotal.setValue(catidad.getValue() * precio.getValue());
				
			}
		});
		
	}


	private void evidenceAction(Combobox c, final Label descrp, Label unidadamedida) {
		c.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Combobox target = (Combobox)event.getTarget();
				Listitem li = (Listitem)target.getParent().getParent().getParent();
				
				descrp.setValue(((Producto) c.getSelectedItem().getValue()).getNombre());
				unidadamedida.setValue(((Producto) c.getSelectedItem().getValue()).getUnidadMedida().getUnidadMedida());
				System.out.println(""+((Producto) c.getSelectedItem().getValue()).getNombre());
				
			}
		});
		
	}


	private void sendAction(A send){
		send.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				A target = (A)event.getTarget();
				Listitem li = (Listitem)target.getParent().getParent();
				
				//ProspectionController controller = (ProspectionController)SpringUtil.getBean("prospectionController");
				//controller.showSendProvidersModal(li);
			}
		});
	}
	
	
		

}
