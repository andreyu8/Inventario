package com.seidor.inventario.validation;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Textbox;

public class RFCMandatoryValidator implements Constraint {
	
	private static final String EXPRESSION_DATE = "[0-9]{2}" + "(0?[1-9]|1[012])" + "(0[1-9]|[12][0-9]|3[01])";
	public static final String EXPRESSION_RFC = "[A-Z&]{4}" + EXPRESSION_DATE + "[A-Z0-9]{3}";
	public static final String EXPRESSION_RFC_SHORT = "[A-Z&]{3}" + EXPRESSION_DATE + "[A-Z0-9]{3}";
	
	public void validate(Component comp, Object value) throws WrongValueException {
		if (value == null || value.toString().trim().length() == 0) {
			throw new WrongValueException(comp, "Debe ingresar un valor");
		}
		
		String rfc = ((String)value).toUpperCase();
		
		if (!rfc.toUpperCase().matches(EXPRESSION_RFC)){
			if (!rfc.toUpperCase().matches(EXPRESSION_RFC_SHORT)){
				throw new WrongValueException(comp, "Debe ingresar un valor con formato LLLL[año][mes][día]XXX o LLL[año][mes][día]XXX");
			}
		}
		
		Textbox rfctb = (Textbox)comp;
		Constraint constraint = rfctb.getConstraint();
		rfctb.setConstraint((Constraint)null);
		rfctb.setValue(rfc);
		rfctb.setConstraint(constraint);
	}
}
