package com.seidor.inventario.validation;

import java.math.BigInteger;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class BigintboxMandatoryValidator implements Constraint {

	public void validate(Component comp, Object value) throws WrongValueException {
		if (value == null || value.toString().trim().length() == 0) {
			throw new WrongValueException(comp, "Debe ingresar un valor");
		}
		
		try {
			BigInteger number = new BigInteger(value.toString());
			if (number.compareTo(new BigInteger("0")) < 0) {
				throw new WrongValueException(comp, "Debe ingresar un número válido");
			}
		} catch(NumberFormatException ex){
			throw new WrongValueException(comp, "Debe ingresar un número");
		}
	}
}
