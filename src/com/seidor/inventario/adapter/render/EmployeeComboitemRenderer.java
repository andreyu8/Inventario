package com.seidor.inventario.adapter.render;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ComboitemRendererExt;

import com.seidor.inventario.model.Empleado;

public class EmployeeComboitemRenderer implements ComboitemRenderer<Empleado>, ComboitemRendererExt {
	
	public Comboitem newComboitem(Combobox combobox) {
		Comboitem comboitem = new Comboitem();
		comboitem.setParent(combobox);
		return comboitem;
	}
	
	public void render(Comboitem comboitem, Empleado data, int index) throws Exception {
		comboitem.setLabel(data.getNombre().trim()+" "+data.getAPaterno().trim() + " "+data.getAMaterno().trim());
		comboitem.setValue(data);
	}
	
		
}