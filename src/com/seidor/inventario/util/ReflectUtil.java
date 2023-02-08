package com.seidor.inventario.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import com.seidor.inventario.exception.BusinessException;

public class ReflectUtil {
	private static int modifierFromStringForVar(String s) {
		int m = 0x0;
		if ("public".equals(s))
			m |= Modifier.PUBLIC;
		else if ("protected".equals(s))
			m |= Modifier.PROTECTED;
		else if ("private".equals(s))
			m |= Modifier.PRIVATE;
		else if ("static".equals(s))
			m |= Modifier.STATIC;
		else if ("final".equals(s))
			m |= Modifier.FINAL;
		else if ("transient".equals(s))
			m |= Modifier.TRANSIENT;
		else if ("volatile".equals(s))
			m |= Modifier.VOLATILE;
		return m;
	}

	public static String getValueGetter(Object object, String attribute) {	
		Class objectClass = object.getClass();
		attribute = StringUtil.capitalize(StringUtil.getAttributeName(attribute));
		
		try {
			Method getMethod = objectClass.getMethod("get" + attribute, new Class[]{});
			return invokeMethodS(getMethod, object);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("Ocurrio un error al tratar de obtener el valor de la propiedad " + attribute + " de un objeto de la clase " + objectClass.getName());
		}
		
	}
	
	public static void setValueSetterS(Object object, String attribute, String value){	
		Class objectClass = object.getClass();
		attribute = StringUtil.getAttributeName(attribute);
		String name = StringUtil.capitalize(attribute);
		
		try {
			Method setMethod = objectClass.getMethod("set" + StringUtil.capitalize(name), new Class[]{String.class});
			setMethod.invoke(object, new Object[]{value});
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("Ocurrio un error al tratar de asignar el valor de la propiedad " + name + " de la clase " + objectClass.getName());
		}		
	}
	
	public static void setValueSetter(Object object, String attribute, Object value){	
		Class objectClass = object.getClass();
		attribute = StringUtil.getAttributeName(attribute);
		String name = StringUtil.capitalize(attribute);
		
		try {
			Method setMethod = objectClass.getMethod("set" + StringUtil.capitalize(name), new Class[]{objectClass.getDeclaredField(attribute).getType()});
			setMethod.invoke(object, new Object[]{value});
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("Ocurrio un error al tratar de asignar el valor de la propiedad " + name + " de la clase " + objectClass.getName());
		}		
	}


	public static String invokeMethodS(Method getMethod, Object object) {
		if (getMethod != null) {
			Object result;
			try {
				result = getMethod.invoke(object, new Object[]{});
				return result == null ? "" : result.toString();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public static Object invokeMethodO(Method getMethod, Object object) {
		if (getMethod != null) {
			Object result;
			try {
				result = getMethod.invoke(object, null);
				return result;
			} catch (IllegalAccessException e) {
				System.out.println("No se encuentro metodo referenciado.");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				System.out.println("No se encuentro metodo referenciado.");
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				System.out.println("No se encuentro metodo referenciado.");
				e.printStackTrace();
			}
		}
		return null;
	}

	public static <T> Field[] getFields(T object) {
		return object.getClass().getDeclaredFields();
	}

	public static <T> void getMethods(T object) {
		for (Method method : object.getClass().getDeclaredMethods()) {
			System.out.println(method.getName());
		}
	}

	public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
		fields.addAll(Arrays.asList(type.getDeclaredFields()));

		if (type.getSuperclass() != null) {
			fields = getAllFields(fields, type.getSuperclass());
		}

		return fields;
	}
	
	public static Boolean isValidField(Class objectClass, String field){
		try {
			objectClass.getDeclaredField(field);
		} catch (NoSuchFieldException ex) {
			return false;
		}
		return true;
	}

	public static String[] getArrayFieldsName(List<Field> fields) {
		String[] nombres = new String[fields.size()];
		for (int i = 0; i < fields.size(); i++) {
			nombres[i] = fields.get(i).getName();
		}

		return nombres;
	}

	public static String[] getNameFields(Field[] fields) {
		String[] nombres = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			nombres[i] = fields[i].getName();
			System.out.println("Campo:" + nombres[i]);
		}

		return nombres;
	}
}
