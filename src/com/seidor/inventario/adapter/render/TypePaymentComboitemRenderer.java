package com.seidor.inventario.adapter.render;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ComboitemRendererExt;

import com.seidor.inventario.model.TipoPago;

public class TypePaymentComboitemRenderer implements ComboitemRenderer<TipoPago>, ComboitemRendererExt {
	
	public Comboitem newComboitem(Combobox combobox) {
		Comboitem comboitem = new Comboitem();
		comboitem.setParent(combobox);
		return comboitem;
	}
	
	public void render(Comboitem comboitem, TipoPago data, int index) throws Exception {
		comboitem.setLabel(data.getTipoPago());
		comboitem.setValue(data);
	}
	
}	