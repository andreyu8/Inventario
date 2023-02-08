package com.seidor.inventario.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class EmailboxValidator implements Constraint {

	public void validate(Component comp, Object value) throws WrongValueException {
		if (value != null && value.toString().trim().length() > 0) {
			
			String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
	        
			Matcher matcher = pattern.matcher(value.toString());
			if (!matcher.matches()) {
				throw new WrongValueException(comp, "Debe especificar una dirección de correo válida");
			}
			
		}
	}
}
