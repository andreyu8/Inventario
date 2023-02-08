package com.seidor.inventario.validation;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class DateboxMandatoryValidator implements Constraint {

	public void validate(Component comp, Object value) throws WrongValueException {
		if (value == null) {
			throw new WrongValueException(comp, "Debe ingresar un valor");
		}
	}
}
