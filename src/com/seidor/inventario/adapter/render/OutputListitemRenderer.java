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

import com.seidor.inventario.adapter.beans.OutBean;
import com.seidor.inventario.adapter.listitem.OutputitemAdapter;
import com.seidor.inventario.controller.CloseProjectController;
import com.seidor.inventario.controller.ProductController;
import com.seidor.inventario.inroweditablecomps.EditableListitem;
import com.seidor.inventario.inroweditablecomps.EditableListitemRenderer;
import com.seidor.inventario.inroweditablecomps.IREditableCheckbox;
import com.seidor.inventario.inroweditablecomps.IREditableIntbox;
import com.seidor.inventario.manager.ProductManager;
import com.seidor.inventario.navigation.Action;
import com.seidor.inventario.util.DateFormatUtil;

public class OutputListitemRenderer extends EditableListitemRenderer{
	
	public OutputListitemRenderer() {
		super();
	}
	
	
	@SuppressWarnings("unused")
	public void render(Listitem listitem, Object data, int index) throws Exception {
		
		OutputitemAdapter oia = (OutputitemAdapter) data;
		OutBean ob = oia.getOutBean();
		
		
		final Listbox lb = listitem.getListbox();
		final EditableListitem editListitem = (EditableListitem) listitem;
		listitem.setValue(oia);
		
		
		Hlayout cbhl= new Hlayout();
		new IREditableCheckbox().setParent(cbhl);
		cbhl.setParent(this.newListcell(editListitem));
		//showCantBack (cblb, ob);
		
		Hlayout idhl= new Hlayout();
		Label idlb= new Label (""+ob.getSalida().getIdSalida());
		idlb.setParent(idhl);
		idhl.setParent(this.newListcell(editListitem));
		
		
		ProductManager productManager = (ProductManager) SpringUtil.getBean("productManager");
		
		Hlayout hl3= new Hlayout();
		new Label (productManager.get(ob.getSalida().getProducto().getIdProducto()).getCategoria().getCategoria()).setParent(hl3);
		hl3.setParent(this.newListcell(editListitem));
		
		
		Hlayout hl4= new Hlayout();
		new Label (ob.getSalida().getProducto().getCodigo()).setParent(hl4);
		hl4.setParent(this.newListcell(editListitem));
		
		
		Hlayout hl5= new Hlayout();
		new Label (ob.getSalida().getProducto().getNombre()).setParent(hl5);
		hl5.setParent(this.newListcell(editListitem));
		
		Hlayout hl6= new Hlayout();
		new Label (""+ob.getSalida().getCantidad()).setParent(hl6);
		hl6.setParent(this.newListcell(editListitem));
		
		Hlayout hl7= new Hlayout();
		new Label (DateFormatUtil.getFormatedDate(ob.getSalida().getFecha(), false)).setParent(hl7);
		hl7.setParent(this.newListcell(editListitem));
		
		Hlayout hl8= new Hlayout();
		new IREditableIntbox(ob.getCantidad()).setParent(hl8);
		hl8.setParent(this.newListcell(editListitem));
		
		
		Action action = new Action() {
			public void execute() {					
				CloseProjectController controller = (CloseProjectController)SpringUtil.getBean("closeProjectController");  
				controller.reintegroProductos (editListitem);
			}
		};
		
		//if(hasEditProfile(ob.getSalida().getIdSalida())) {
			if (ob.getSalida().getIdSalida() == null) {
				this.createControlButtonsWhitoutDelete(editListitem, action, true);
			}
			else {
				this.createControlButtonsWhitoutDelete(editListitem, action, false);
			}
		
			this.newListcell(editListitem);
		//}
		
	}


	private void showCantBack(Checkbox cblb, OutBean ob) {
		
		
		/*cblb.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
		
				Listitem li = (Listitem) cblb.getParent().getParent().getParent();
				Listbox lb = li.getListbox();
		
				final Component win= (Component) lb.getAttribute("win");
				
				
			}
		});*/ 
		
		
	}	
	

}
