package com.seidor.inventario.adapter.render;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ComboitemRendererExt;

import com.seidor.inventario.model.EstatusOrdenCompra;

public class StatusTypeOrderComboitemRenderer implements ComboitemRenderer<EstatusOrdenCompra>, ComboitemRendererExt {
	
	public Comboitem newComboitem(Combobox combobox) {
		Comboitem comboitem = new Comboitem();
		comboitem.setParent(combobox);
		return comboitem;
	}
	
	public void render(Comboitem comboitem, EstatusOrdenCompra data, int index) throws Exception {
		comboitem.setLabel(data.getEstatusOrdenCompra());
		comboitem.setValue(data);
	}
	
}	