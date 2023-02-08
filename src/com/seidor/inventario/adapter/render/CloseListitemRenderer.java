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
import com.seidor.inventario.adapter.listitem.CloseitemAdapter;
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

public class CloseListitemRenderer extends EditableListitemRenderer{
	
	public CloseListitemRenderer() {
		super();
	}
	
	
	@SuppressWarnings("unused")
	public void render(Listitem listitem, Object data, int index) throws Exception {
		
		CloseitemAdapter oia = (CloseitemAdapter) data;
		CloseBean ob = oia.getCloseBean();
		
		
		final Listbox lb = listitem.getListbox();
		final EditableListitem editListitem = (EditableListitem) listitem;
		listitem.setValue(oia);
		
		Hlayout hl1= new Hlayout();
		new Label (""+ob.getFamilia()).setParent(hl1);
		hl1.setParent(this.newListcell(editListitem));
		
		Hlayout hl2= new Hlayout();
		new Label (ob.getCodigo()).setParent(hl2);
		hl2.setParent(this.newListcell(editListitem));
		
		
		Hlayout hl3= new Hlayout();
		new Label (ob.getProducto()).setParent(hl3);
		hl3.setParent(this.newListcell(editListitem));
		
		Hlayout hl4= new Hlayout();
		new Label (ob.getUnidadMedida()).setParent(hl4);
		hl4.setParent(this.newListcell(editListitem));
		
		Hlayout hl5= new Hlayout();
		new Label (""+ob.getCantidadEntrada()).setParent(hl5);
		hl5.setParent(this.newListcell(editListitem));
		
		//salida
		Hlayout h16= new Hlayout();
		new Label (""+ob.getFamilia_s()).setParent(h16);
		h16.setParent(this.newListcell(editListitem));
		
		Hlayout hl7= new Hlayout();
		new Label (ob.getCodigo_s()).setParent(hl7);
		hl7.setParent(this.newListcell(editListitem));
		
		Hlayout hl8= new Hlayout();
		new Label (ob.getProducto_s()).setParent(hl8);
		hl8.setParent(this.newListcell(editListitem));
		
		Hlayout hl9= new Hlayout();
		new Label (ob.getUnidadMedida_s()).setParent(hl9);
		hl9.setParent(this.newListcell(editListitem));
		
		Hlayout hl10= new Hlayout();
		new Label (""+ob.getCantidadSalida()).setParent(hl10);
		hl10.setParent(this.newListcell(editListitem));
		
		Hlayout hl11= new Hlayout();
		new Label (""+ob.getDiferencia()).setParent(hl11);
		hl11.setParent(this.newListcell(editListitem));
		
		Hlayout hl12= new Hlayout();
		new IREditableIntbox(ob.getDevoluciones()).setParent(hl12);
		hl12.setParent(this.newListcell(editListitem));
		
		
		Action action = new Action() {
			public void execute() {					
				CloseProjectController controller = (CloseProjectController)SpringUtil.getBean("closeProjectController");  
				controller.reintegroProductosClose (editListitem);
			}
		}; 
		
		//if(hasEditProfile(ob.getSalida().getIdSalida())) {
			if (ob.getIdProducto() == 0) {
				this.createControlButtonsWhitoutDelete(editListitem, action, true);
			}
			else {
				this.createControlButtonsWhitoutDelete(editListitem, action, false);
			}
		
			this.newListcell(editListitem);
		//}
		
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
