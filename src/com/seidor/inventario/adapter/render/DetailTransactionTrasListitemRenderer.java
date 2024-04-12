package com.seidor.inventario.adapter.render;

import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.seidor.inventario.adapter.listitem.DetailTransactionitemAdapter;
import com.seidor.inventario.controller.TransactionDetailController;
import com.seidor.inventario.controller.TraspasosController;
import com.seidor.inventario.inroweditablecomps.EditableListitem;
import com.seidor.inventario.inroweditablecomps.EditableListitemRenderer;
import com.seidor.inventario.inroweditablecomps.IREditableDoublebox;
import com.seidor.inventario.model.DetalleMovimiento;
import com.seidor.inventario.navigation.Action;
import com.seidor.inventario.navigation.NavigationControl;

public class DetailTransactionTrasListitemRenderer  extends EditableListitemRenderer{
	
	private Integer idStatusMovimiento;
	
	public DetailTransactionTrasListitemRenderer(Integer idStatusMovimiento) {
		super();
		this.idStatusMovimiento= idStatusMovimiento; 
	}
	
	
	@SuppressWarnings("unused")
	public void render(Listitem listitem, Object data, int index) throws Exception {
		
		DetailTransactionitemAdapter docia = (DetailTransactionitemAdapter) data;
		DetalleMovimiento ocd = docia.getDetalleMovimiento();
		
		
		final Listbox lb = listitem.getListbox();
		final EditableListitem editListitem = (EditableListitem) listitem;
		listitem.setValue(ocd);
		
		Hlayout hl1= new Hlayout();
		new Label (ocd.getProducto().getCodigo()).setParent(hl1);
		hl1.setParent(this.newListcell(editListitem));
		
		Hlayout hl2= new Hlayout();
		new Label (ocd.getProducto().getNombre()).setParent(hl2);
		hl2.setParent(this.newListcell(editListitem));
		
		
		Hlayout hl3= new Hlayout();
		new Label (""+ocd.getCantidadTotal()).setParent(hl3);
		hl3.setParent(this.newListcell(editListitem));
		
		Hlayout hl4= new Hlayout();
		new Label (ocd.getProducto().getUnidadMedida().getDescripcion()).setParent(hl4);
		hl4.setParent(this.newListcell(editListitem));
		
		Hlayout hl5= new Hlayout();
		new Label (""+ocd.getPrecioUnitario()).setParent(hl5);
		hl5.setParent(this.newListcell(editListitem));
		
		//salida
		Hlayout h16= new Hlayout();
		new Label (""+ (ocd.getCantidadTotal() * ocd.getPrecioUnitario().doubleValue())).setParent(h16);
		h16.setParent(this.newListcell(editListitem));
		
		
		Hlayout hl12= new Hlayout();
		new IREditableDoublebox(ocd.getCantidad()).setParent(hl12);
		hl12.setParent(this.newListcell(editListitem));
		
		
		Action action = new Action() {
			public void execute() {		
				TraspasosController  traspasosController = (TraspasosController)SpringUtil.getBean("traspasosController");
				traspasosController.saveOrUpdateTraspasoAlmacen (editListitem, idStatusMovimiento);
			}
		}; 
		
		
		EventListener<Event> deleteEvent = new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Listitem li = (Listitem)event.getTarget().getParent().getParent().getParent();
				Listbox lb = li.getListbox();
				lb.setSelectedItem(li);
				Action deleteAction = new Action() {
					public void execute(){
						
						TransactionDetailController controller = (TransactionDetailController)SpringUtil.getBean("transactionDetailController");
						controller.deleteProduct(editListitem);
						//lb.removeItemAt(li.getIndex());
					}
				};
				NavigationControl.openModalWindowWithMessage("¿Realmente desea borrar el producto seleccionado?", deleteAction);
			}
		};
		

		if (ocd.getIdDetalleMovimiento() > 0) {
			this.createControlButtons(editListitem, action, deleteEvent, true);
		}
		else {
			this.createControlButtons(editListitem, action, deleteEvent, false);
		}
		
		
	
		this.newListcell(editListitem);
		
	}
	

}
