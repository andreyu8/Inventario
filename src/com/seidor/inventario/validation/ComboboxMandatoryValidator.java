package com.seidor.inventario.validation;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Constraint;

public class ComboboxMandatoryValidator implements Constraint {
	
	public void validate(Component comp, Object value) throws WrongValueException {
		if (value == null || value.toString().length() == 0) {
			throw new WrongValueException(comp, "Debe ingresar un valor");
		}
		else {
			Combobox combo = (Combobox)comp;
			boolean found = false;
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
