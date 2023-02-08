package com.seidor.inventario.validation;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class LongboxMandatoryValidator implements Constraint {

	public void validate(Component comp, Object value) throws WrongValueException {
		if (value == null || value.toString().trim().length() == 0) {
			throw new WrongValueException(comp, "Debe ingresar un valor");
		}
		
		try {
			Long number = Long.parseLong(value.toString());
			if (number.compareTo((long) 0) < 0) {
				throw new WrongValueException(comp, "Debe ingresar un número válido");
			}
		} catch(NumberFormatException ex){
			throw new WrongValueException(comp, "Debe ingresar un número");
		}
	}
}
