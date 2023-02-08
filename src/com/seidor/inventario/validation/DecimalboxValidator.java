package com.seidor.inventario.validation;

import java.math.BigDecimal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class DecimalboxValidator implements Constraint{

	public void validate(Component comp, Object value) throws WrongValueException {
		
		if (value != null){
			BigDecimal number = (BigDecimal)value;
			if(number.compareTo(new BigDecimal("0")) < 0){
				throw new WrongValueException(comp, "Valor fuera de rango");
			}
		}
	
	}

}
