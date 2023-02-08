package com.seidor.inventario.validation;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class DoubleboxMandatoryValidator implements Constraint{

	public void validate(Component comp, Object value) throws WrongValueException {
		
		if (value == null || value.toString().trim().length() == 0) {
			throw new WrongValueException(comp, "Debe ingresar un valor");
		}
		
		Double number = (Double)value;
		if(number.compareTo(Double.valueOf(9999999999.99)) > 0 || number < 0){
			throw new WrongValueException(comp, "Valor fuera de rango");
		}
	
	}

}
