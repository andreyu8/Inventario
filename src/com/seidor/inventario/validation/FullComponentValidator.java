package com.seidor.inventario.validation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.NumberInputElement;


public class FullComponentValidator {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void validate(Component comp) {
		if (comp instanceof InputElement) {
			Class compClass = comp.getClass();
			try {
				Method m = compClass.getMethod("getValue", new Class[]{});
				@SuppressWarnings("unused")
				Object value = m.invoke(comp, new Object[]{});
			}
			catch (NoSuchMethodException ex) {ex.printStackTrace();}
			catch (IllegalAccessException ex) {ex.printStackTrace();}
			catch (InvocationTargetException ex) {
				Throwable th = ex.getCause();
				if (th instanceof WrongValueException) {
					WrongValueException wrong = (WrongValueException)th;
					throw wrong;
				}
			}
		}else if (comp instanceof NumberInputElement) {
			Class compClass = comp.getClass();
			try {
				Method m = compClass.getMethod("getValue", new Class[]{});
				@SuppressWarnings("unused")
				Object value = m.invoke(comp, new Object[]{});
			}
			catch (NoSuchMethodException ex) {ex.printStackTrace();}
			catch (IllegalAccessException ex) {ex.printStackTrace();}
			catch (InvocationTargetException ex) {
				Throwable th = ex.getCause();
				if (th instanceof WrongValueException) {
					WrongValueException wrong = (WrongValueException)th;
					throw wrong;
				}
			}			
		}
		else {
			List children = comp.getChildren();
			if (children != null && children.size() > 0) {
				for (int i = 0; i<children.size(); i++) {
					validate((Component)children.get(i));
				}
			}
		}
	}
}
