package com.seidor.inventario.validation;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Textbox;

public class CURPValidator implements Constraint {

	private static final String EXPRESSION_CURP = "[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}"
			+ "(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])"
			+ "[HM]{1}"
			+ "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)"
			+ "[B-DF-HJ-NP-TV-Z]{3}"
			+ "[0-9A-Z]{1}"
			+ "[0-9]{1}";
	
	public void validate(Component comp, Object value) throws WrongValueException {
		String curp;
		if (value != null && value.toString().trim().length() > 0) {
			curp = ((String)value).toUpperCase();

			if (!curp.toUpperCase().matches(EXPRESSION_CURP)){
				throw new WrongValueException(comp, "Debe ingresar una clave válida");
			}

			Textbox curptb = (Textbox)comp;
			Constraint constraint = curptb.getConstraint();
			curptb.setConstraint((Constraint)null);
			curptb.setValue(curp);
			curptb.setConstraint(constraint);
		}

	}

}
