package com.seidor.inventario.adapter.render;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ComboitemRendererExt;

import com.seidor.inventario.model.Ubicacion;

public class LocationComboitemRenderer implements ComboitemRenderer<Ubicacion>, ComboitemRendererExt {
	
	public Comboitem newComboitem(Combobox combobox) {
		Comboitem comboitem = new Comboitem();
		comboitem.setParent(combobox);
		return comboitem;
	}
	
	public void render(Comboitem comboitem, Ubicacion data, int index) throws Exception {
		comboitem.setLabel(data.getUbicacion());
		comboitem.setValue(data);
	}
	
		
}