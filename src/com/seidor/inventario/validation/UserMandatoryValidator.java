package com.seidor.inventario.validation;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Constraint;

import com.seidor.inventario.dao.UserDAO;

public class UserMandatoryValidator implements Constraint {
	
	public void validate(Component comp, Object value) throws WrongValueException {
		if (value == null || value.toString().trim().length() == 0) {
			throw new WrongValueException(comp, "Debe ingresar un valor");
		}
		
		if (value == null || value.toString().trim().length() < 6) {
			throw new WrongValueException(comp, "El usuario debe tener al menos 6 caracteres");
		}
		
		UserDAO udao = (UserDAO)SpringUtil.getBean("userDao");
		if (udao.get(value.toString()) != null) {
			throw new WrongValueException(comp, "Usuario no disponible");
		}
	}
}
