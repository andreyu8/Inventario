package com.seidor.inventario.validation;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Constraint;

public class ComboboxValidator implements Constraint {
	
	public void validate(Component comp, Object value) throws WrongValueException {

		Combobox combo = (Combobox)comp;
		boolean found = false;
		
		String cValue = (String)value;
		if (cValue.length() > 0) {
			List<Comboitem> list = combo.getItems();
			for (int i=0; i<list.size(); i++) {
				Comboitem item = combo.getItemAtIndex(i);
				String label = item.getLabel();
				if (label.equals(value)) {
					found = true;
					break;
				}
			}
			if (!found) {
				throw new WrongValueException(comp, "Debe seleccionar un elemento de la lista");
			}
		}


	}
	
}
