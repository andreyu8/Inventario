package com.seidor.inventario.adapter.render;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ComboitemRendererExt;

import com.seidor.inventario.model.EstatusProyecto;

public class EstatusComboItemRenderer implements ComboitemRenderer<EstatusProyecto>, ComboitemRendererExt {
	
	public Comboitem newComboitem(Combobox combobox) {
		Comboitem comboitem = new Comboitem();
		comboitem.setParent(combobox);
		return comboitem;
	}
	
	public void render(Comboitem comboitem, EstatusProyecto data, int index) throws Exception {
		comboitem.setLabel(data.getDescripcion());
		comboitem.setValue(data);
	}
	
		
}
