package com.seidor.inventario.adapter.render;

import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.seidor.inventario.adapter.beans.CierreBean;
import com.seidor.inventario.adapter.beans.CloseBean;
import com.seidor.inventario.adapter.listitem.DetailOCitemAdapter;
import com.seidor.inventario.adapter.listitem.OutputitemAdapter;
import com.seidor.inventario.controller.CloseProjectController;
import com.seidor.inventario.controller.InvoiceController;
import com.seidor.inventario.controller.ProductController;
import com.seidor.inventario.controller.TransactionController;
import com.seidor.inventario.inroweditablecomps.EditableListitem;
import com.seidor.inventario.inroweditablecomps.EditableListitemRenderer;
import com.seidor.inventario.inroweditablecomps.IREditableCheckbox;
import com.seidor.inventario.inroweditablecomps.IREditableDoublebox;
import com.seidor.inventario.inroweditablecomps.IREditableIntbox;
import com.seidor.inventario.manager.ProductManager;
import com.seidor.inventario.model.DetalleOrdenCompra;
import com.seidor.inventario.navigation.Action;
import com.seidor.inventario.util.DateFormatUtil;

public class DetailOCAlmacenListitemRenderer extends EditableListitemRenderer{
	
	public DetailOCAlmacenListitemRenderer() {
		super();
	}
	
	
	@SuppressWarnings("unused")
	public void render(Listitem listitem, Object data, int index) throws Exception {
		
		DetailOCitemAdapter docia = (DetailOCitemAdapter) data;
		DetalleOrdenCompra ocd = docia.getDetalleOrdenCompra();
		
		
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
		new Label (""+ocd.getCantidad()).setParent(hl3);
		hl3.setParent(this.newListcell(editListitem));
		
		Hlayout hl4= new Hlayout();
		new Label (ocd.getProducto().getUnidadMedida().getDescripcion()).setParent(hl4);
		hl4.setParent(this.newListcell(editListitem));
		
		Hlayout hl5= new Hlayout();
		new Label (""+ocd.getPrecioUnitario()).setParent(hl5);
		hl5.setParent(this.newListcell(editListitem));
		
		//salida
		Hlayout h16= new Hlayout();
		new Label (""+ (ocd.getCantidad() * ocd.getPrecioUnitario().doubleValue())).setParent(h16);
		h16.setParent(this.newListcell(editListitem));
		
		
		Hlayout hl12= new Hlayout();
		new IREditableDoublebox(ocd.getCantidadFactura()).setParent(hl12);
		hl12.setParent(this.newListcell(editListitem));
		
		
		Action action = new Action() {
			public void execute() {		
				InvoiceController  invoiceController = (InvoiceController)SpringUtil.getBean("invoiceController");
				invoiceController.agregarEntradasAlmacen (editListitem);
				
				/*TransactionController  transactionController = (TransactionController)SpringUtil.getBean("invoiceController");
				transactionController.agregarEntradasAlmacen (editListitem);*/
			}
		}; 
		

		if (ocd.getIdDetalleOc() == 0) {
			this.createControlButtonsWhitoutDelete(editListitem, action, true);
		}
		else {
			this.createControlButtonsWhitoutDelete(editListitem, action, false);
		}
		
		
	
		this.newListcell(editListitem);
		
	}


	private void showCantBack(Checkbox cblb, CloseBean ob) {
		
		/*cblb.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
		
				Listitem li = (Listitem) cblb.getParent().getParent().getParent();
				Listbox lb = li.getListbox();
		
				final Component win= (Component) lb.getAttribute("win");
				
				
			}
		});*/ 
		
	}
	

}
