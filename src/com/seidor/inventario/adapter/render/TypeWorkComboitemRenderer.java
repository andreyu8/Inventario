package com.seidor.inventario.adapter.render;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ComboitemRendererExt;

import com.seidor.inventario.model.TipoTrabajo;

public class TypeWorkComboitemRenderer implements ComboitemRenderer<TipoTrabajo>, ComboitemRendererExt {
	
	public Comboitem newComboitem(Combobox combobox) {
		Comboitem comboitem = new Comboitem();
		comboitem.setParent(combobox);
		return comboitem;
	}
	
	public void render(Comboitem comboitem, TipoTrabajo data, int index) throws Exception {
		comboitem.setLabel(data.getTipoTrabajo());
		comboitem.setValue(data);
	}
	
		
}
