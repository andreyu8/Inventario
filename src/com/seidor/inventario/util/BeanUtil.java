package com.seidor.inventario.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.LazyInitializationException;
import org.hibernate.collection.internal.PersistentSet;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class BeanUtil {
	
	public static Object createCopy(Object source) {
		Object destination = null;
		
		try {
			Class objectClass = source.getClass();
			destination = objectClass.getDeclaredConstructor().newInstance();
			Field[] properties = objectClass.getDeclaredFields();
			
			int propertiesLength = properties.length;
			for (int i= 0; i < propertiesLength; i++){
				Field currentProperty = properties[i];
				String methodPrefix;
				if (currentProperty.getGenericType().toString().compareTo("boolean") == 0) {
					methodPrefix="is";
				}
				else {
					methodPrefix="get";
				}
				String methodName = String.valueOf(currentProperty.getName().charAt(0)).toUpperCase() + currentProperty.getName().substring(1);
				Method getMethod = null;
				try {
					getMethod = objectClass.getMethod(methodPrefix + methodName, new Class[]{});
				}
				catch (NoSuchMethodException ex) {
					continue;
				}
				Object propertyValue = getMethod.invoke(source, new Object[]{});
				if (propertyValue != null) {
					Method setMethod = objectClass.getMethod("set" + methodName, new Class[]{currentProperty.getType()});
					if (propertyValue instanceof Integer
					 || propertyValue instanceof Boolean
					 || propertyValue instanceof Date
					 || propertyValue instanceof String
					 || propertyValue instanceof BigDecimal
					 || propertyValue instanceof Double
					 || propertyValue instanceof Long
					 || propertyValue instanceof byte[]) {

						setMethod.invoke(destination, new Object[]{propertyValue});
					}
					else if (propertyValue instanceof ArrayList) {
						ArrayList array = (ArrayList)propertyValue;

						if (array != null) {
							ArrayList arrayCopy = new ArrayList();
							setMethod.invoke(destination, new Object[]{arrayCopy});
							Integer size = array.size();
							for (int j=0; j < size; j++) {
								Object aprop = array.get(j);
								if (aprop instanceof Integer
									|| aprop instanceof Boolean
									|| aprop instanceof Date
									|| aprop instanceof String
									|| aprop instanceof BigDecimal
									|| aprop instanceof Double
									|| aprop instanceof Long
									|| aprop instanceof byte[]) {
									
									arrayCopy.add(aprop);
								}
								else {
									arrayCopy.add(createCopy(aprop));
								}
							}
						}

					}
					else if (propertyValue instanceof PersistentSet) {
						
					}
					else if (propertyValue instanceof Set) {
						
					}
					else {
						setMethod.invoke(destination, new Object[]{createCopy(propertyValue)});
					}
				}
			}
		} 
		catch (NoSuchMethodException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		catch (IllegalAccessException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		catch (InvocationTargetException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		catch (InstantiationException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		
		return destination;
	}
	
	public static void copy(Object source, Object destination) {
		
		try {
			Class objectClass = source.getClass();
			Class destinationClass = destination.getClass();
			Field[] properties = objectClass.getDeclaredFields();

			int propertiesLength = properties.length;
			for (int i= 0; i < propertiesLength; i++){
				Field currentProperty = properties[i];
				String methodPrefix;
				if (currentProperty.getGenericType().toString().compareTo("boolean")==0) {
					methodPrefix="is";
				}
				else {
					methodPrefix="get";
				}
				String methodName = String.valueOf(currentProperty.getName().charAt(0)).toUpperCase() + currentProperty.getName().substring(1);
				Method getMethod = null;
				try {
					getMethod = objectClass.getMethod(methodPrefix + methodName, new Class[]{});
				}
				catch (NoSuchMethodException ex) {
					continue;
				}
				Object propertyValue = getMethod.invoke(source, new Object[]{});
				if (propertyValue != null) {
					Method setMethod = destinationClass.getMethod("set" + methodName, new Class[]{currentProperty.getType()});
					if (propertyValue instanceof Integer
					 || propertyValue instanceof Boolean
					 || propertyValue instanceof Date
					 || propertyValue instanceof String
					 || propertyValue instanceof BigDecimal
					 || propertyValue instanceof Double
					 || propertyValue instanceof Long
					 || propertyValue instanceof byte[]) {

						setMethod.invoke(destination, new Object[]{propertyValue});
					}
					else if (propertyValue instanceof ArrayList) {
						ArrayList array = (ArrayList)propertyValue;

						if (array != null) {
							ArrayList arrayCopy = new ArrayList();
							setMethod.invoke(destination, new Object[]{arrayCopy});
							Integer size = array.size();
							for (int j=0; j < size; j++) {
								arrayCopy.add(createCopy(array.get(j)));
							}
						}

					}
					else if (propertyValue instanceof PersistentSet) {
						PersistentSet set = (PersistentSet)propertyValue;

						if (set.wasInitialized()) {
							HashSet psCopy = new HashSet();
							setMethod.invoke(destination, new Object[]{psCopy});
							Object[] objects = set.toArray();
							for (int j=0; j<objects.length; j++) {
								psCopy.add(createCopy(objects[j]));
							}
						}

					}
					else if (propertyValue instanceof HashSet) {
						HashSet set = (HashSet) propertyValue;
						HashSet hsCopy = new HashSet();
						setMethod.invoke(destination, new Object[]{hsCopy});
						Iterator it = set.iterator();
						while (it.hasNext()) {
							hsCopy.add(createCopy(it.next()));
						}
					}
					else {
						setMethod.invoke(destination, new Object[]{createCopy(propertyValue)});
					}
				}
			}
		} 
		catch (NoSuchMethodException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		catch (IllegalAccessException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		catch (InvocationTargetException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		
	}
	
	public static void cleanSystemData(Object object) {
		Class objectClass = object.getClass();
		
		try {
			Method setIdMethod = objectClass.getMethod("setId", new Class[]{objectClass.getDeclaredField("id").getType()});
			Method setFlgDltMethod = objectClass.getMethod("setFdl", new Class[]{objectClass.getDeclaredField("fdl").getType()});
			Method setCreatedByUserMethod = objectClass.getMethod("setCbu", new Class[]{objectClass.getDeclaredField("cbu").getType()});
			Method setCreatedAtMethod = objectClass.getMethod("setCat", new Class[]{objectClass.getDeclaredField("cat").getType()});
			Method setLastUpdateUserMethod = objectClass.getMethod("setLuu", new Class[]{objectClass.getDeclaredField("luu").getType()});
			Method setUpdateAtMethod = objectClass.getMethod("setUat", new Class[]{objectClass.getDeclaredField("uat").getType()});
			
			setIdMethod.invoke(object, new Object[]{null});
			setFlgDltMethod.invoke(object, new Object[]{null});
			setCreatedByUserMethod.invoke(object, new Object[]{null});
			setUpdateAtMethod.invoke(object, new Object[]{null});
			setLastUpdateUserMethod.invoke(object, new Object[]{null});
			setCreatedAtMethod.invoke(object, new Object[]{null});
		} 
		catch (NoSuchMethodException nsmex) {
			throw new RuntimeException(nsmex.getMessage());
		}
		catch (SecurityException sex) {
			throw new RuntimeException(sex.getMessage());
		}
		catch (NoSuchFieldException nsfex) {
			throw new RuntimeException(nsfex.getMessage());
		}
		catch (IllegalArgumentException iaex) {
			throw new RuntimeException(iaex.getMessage());
		} 
		catch (IllegalAccessException iaex) {
			throw new RuntimeException(iaex.getMessage());
		}
		catch (InvocationTargetException itex) {
			throw new RuntimeException(itex.getMessage());
		}
	}
	
	public static void clearLazy(Object object){
		try {
			if (object instanceof Collection){
				Collection collection = (Collection)object;
				if (collection != null) {
					for (Object obj : collection) {
						clearLazy(obj);
					}
				}
			}
			
			Class objectClass = object.getClass();
			Field[] properties = objectClass.getDeclaredFields();
			
			int propertiesLength = properties.length;
			for (int i= 0; i < propertiesLength; i++){
				Field currentProperty = properties[i];
				String methodPrefix;
				if (currentProperty.getGenericType().toString().compareTo("boolean")==0) {
					methodPrefix = "is";
				}
				else {
					methodPrefix = "get";
				}
				String methodName = String.valueOf(currentProperty.getName().charAt(0)).toUpperCase() + currentProperty.getName().substring(1);
				Method getMethod = null;
				try {
					getMethod = objectClass.getMethod(methodPrefix + methodName, new Class[]{});
				}
				catch (NoSuchMethodException ex) {
					continue;
				}
				try {
					
					Object propertyValue = getMethod.invoke(object, new Object[]{});
					
					if (propertyValue != null) {
						
						if (propertyValue instanceof Integer
						 || propertyValue instanceof Boolean
						 || propertyValue instanceof Date
						 || propertyValue instanceof String
						 || propertyValue instanceof BigDecimal
						 || propertyValue instanceof Double
						 || propertyValue instanceof Long
						 || propertyValue instanceof byte[]) {
							
						}
						else if (propertyValue instanceof ArrayList) {
							ArrayList array = (ArrayList)propertyValue;
							if (array != null) {
								for (Object obj : array) {
									clearLazy(obj);
								}
							}
						}
						else if (propertyValue instanceof HashMap) {
							HashMap map = (HashMap)propertyValue;
							if (map != null) {
								for (Object key : map.keySet()) {
									Object mapObj = map.get(key);
									if (mapObj != null) {
										clearLazy(mapObj);
									}
								}
							}
						}
						else if (propertyValue instanceof PersistentSet ||
								 propertyValue instanceof HashSet){
							Method setMethod = objectClass.getMethod("set" + methodName, new Class[]{currentProperty.getType()});
							setMethod.invoke(object, new Object[]{null});
						}
						else {
							clearLazy(propertyValue);
						}
					}
				} catch (LazyInitializationException liex){
					liex.printStackTrace();
					Method setMethod = objectClass.getMethod("set" + methodName, new Class[]{currentProperty.getType()});
					setMethod.invoke(object, new Object[]{null});
				}
			}
		} 
		catch (NoSuchMethodException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		catch (IllegalAccessException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		catch (InvocationTargetException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public static void clearFetched(Object object){
		try {
			if (object instanceof Collection){
				Collection collection = (Collection)object;
				if (collection != null) {
					for (Object obj : collection) {
						clearFetched(obj);
					}
				}
			}
			
			Class objectClass = object.getClass();
			Field[] properties = objectClass.getDeclaredFields();
			
			int propertiesLength = properties.length;
			for (int i= 0; i < propertiesLength; i++){
				Field currentProperty = properties[i];
				
				String methodName = String.valueOf(currentProperty.getName().charAt(0)).toUpperCase() + currentProperty.getName().substring(1);
				Method getMethod = null;
				try {
					getMethod = objectClass.getMethod("get" + methodName, new Class[]{});
				}
				catch (NoSuchMethodException ex) {
					continue;
				}
				try {
					
					Object propertyValue = getMethod.invoke(object, new Object[]{});
					
					
					if (propertyValue != null) {
						
						if (propertyValue instanceof Integer
						 || propertyValue instanceof Boolean
						 || propertyValue instanceof Date
						 || propertyValue instanceof String
						 || propertyValue instanceof BigDecimal
						 || propertyValue instanceof Double
						 || propertyValue instanceof Long
						 || propertyValue instanceof byte[]
						 || propertyValue instanceof ArrayList
						 || propertyValue instanceof PersistentSet
						 || propertyValue instanceof HashSet
						 || propertyValue instanceof HashMap ) {
							
						}
						else if (propertyValue.getClass().getSimpleName().contains("javassist")) {
							Method setMethod = objectClass.getMethod("set" + methodName, new Class[]{currentProperty.getType()});
							setMethod.invoke(object, new Object[]{null});	
						}
						else {
							clearFetched(propertyValue);
						}
					}
				} catch (Exception ex){
					ex.printStackTrace();
					Method setMethod = objectClass.getMethod("set" + methodName, new Class[]{currentProperty.getType()});
					setMethod.invoke(object, new Object[]{null});
				}
			}
		} 
		catch (NoSuchMethodException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		catch (IllegalAccessException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		catch (InvocationTargetException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public static void clearForWS(Object object){
		clearLazy(object);
		clearFetched(object);
	}
	
}