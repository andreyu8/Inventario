package com.seidor.inventario.adapter.render;

import org.zkoss.spring.SpringUtil;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.seidor.inventario.adapter.beans.DevolucionBean;
import com.seidor.inventario.adapter.listitem.DevolucionitemAdapter;
import com.seidor.inventario.controller.DevolucionesController;
import com.seidor.inventario.inroweditablecomps.EditableListitem;
import com.seidor.inventario.inroweditablecomps.EditableListitemRenderer;
import com.seidor.inventario.inroweditablecomps.IREditableDoublebox;
import com.seidor.inventario.navigation.Action;

public class DevolucionListitemRenderer extends EditableListitemRenderer{
	
	public DevolucionListitemRenderer() {
		super();
	}
	
	
	@SuppressWarnings("unused")
	public void render(Listitem listitem, Object data, int index) throws Exception {
		
		DevolucionitemAdapter oia = (DevolucionitemAdapter) data;
		DevolucionBean ob = oia.getDevolucionBean();
		
		
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
		
		//entrada
		Hlayout hl5= new Hlayout();
		new Label (""+ob.getCantidadEntrada()).setParent(hl5);
		hl5.setParent(this.newListcell(editListitem));
		
		//salida
		Hlayout hl10= new Hlayout();
		new Label (""+ob.getCantidadSalida()).setParent(hl10);
		hl10.setParent(this.newListcell(editListitem));
		
		Hlayout hl11= new Hlayout();
		new Label (""+ob.getDiferencia()).setParent(hl11);
		hl11.setParent(this.newListcell(editListitem));
		
		Hlayout hl12= new Hlayout();
		new IREditableDoublebox(ob.getDevoluciones()).setParent(hl12);
		hl12.setParent(this.newListcell(editListitem));
		
		
		Action action = new Action() {
			public void execute() {					
				DevolucionesController controller = (DevolucionesController)SpringUtil.getBean("devolucionesController");  
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


	private void showCantBack(Checkbox cblb, DevolucionBean ob) {
		
		/*cblb.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
		
				Listitem li = (Listitem) cblb.getParent().getParent().getParent();
				Listbox lb = li.getListbox();
		
				final Component win= (Component) lb.getAttribute("win");
				
				
			}
		});*/ 
		
	}
	

}
