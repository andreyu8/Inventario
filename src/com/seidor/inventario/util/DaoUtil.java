package com.seidor.inventario.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@SuppressWarnings("unchecked")
public class DaoUtil {
	
	@SuppressWarnings("rawtypes")
	public static void prepareToSave(Object object) {
		Class objectClass = object.getClass();
		
		try {
			Method setFlgDltMethod = objectClass.getMethod("setFdl", new Class[]{objectClass.getDeclaredField("fdl").getType()});
			Method setCreatedByUserMethod = objectClass.getMethod("setCbu", new Class[]{objectClass.getDeclaredField("cbu").getType()});
			Method setCreatedAtMethod = objectClass.getMethod("setCat", new Class[]{objectClass.getDeclaredField("cat").getType()});
			Method setLastUpdateUserMethod = objectClass.getMethod("setLuu", new Class[]{objectClass.getDeclaredField("luu").getType()});
			Method setUpdateAtMethod = objectClass.getMethod("setUat", new Class[]{objectClass.getDeclaredField("uat").getType()});
			
			Integer userId = SessionUtil.getLoggedUserId();
			
			setFlgDltMethod.invoke(object, new Object[]{false});
			setCreatedByUserMethod.invoke(object, new Object[]{userId});
			setUpdateAtMethod.invoke(object, new Object[]{Calendar.getInstance(DateFormatUtil.getDefaultLocale()).getTime()});
			setLastUpdateUserMethod.invoke(object, new Object[]{userId});
			setCreatedAtMethod.invoke(object, new Object[]{Calendar.getInstance(DateFormatUtil.getDefaultLocale()).getTime()});
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
	
	@SuppressWarnings("rawtypes")
	public static void prepareToUpdate(Object object){
		Class objectClass = object.getClass();
		try {
			Method setLastUpdateUserMethod = objectClass.getMethod("setLuu", new Class[]{objectClass.getDeclaredField("luu").getType()});
			Method setUpdateAtMethod = objectClass.getMethod("setUat", new Class[]{objectClass.getDeclaredField("uat").getType()});
			
			Integer userId = SessionUtil.getLoggedUserId();
			
			setLastUpdateUserMethod.invoke(object, new Object[]{userId});
			setUpdateAtMethod.invoke(object, new Object[]{Calendar.getInstance(DateFormatUtil.getDefaultLocale()).getTime()});
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
	
	@SuppressWarnings("rawtypes")
	public static void prepareToDelete(Object object){
		
		Class objectClass = object.getClass();
		
		try {
			Method setFlagDeletedMethod = objectClass.getMethod("setFdl", new Class[]{objectClass.getDeclaredField("fdl").getType()});
			Method setLastUpdateUserMethod = objectClass.getMethod("setLuu", new Class[]{objectClass.getDeclaredField("luu").getType()});
			Method setUpdateAtMethod = objectClass.getMethod("setUat", new Class[]{objectClass.getDeclaredField("uat").getType()});
			
			Integer userId = SessionUtil.getLoggedUserId();
			
			setFlagDeletedMethod.invoke(object, new Object[]{true});
			setLastUpdateUserMethod.invoke(object, new Object[]{userId});
			setUpdateAtMethod.invoke(object, new Object[] {Calendar.getInstance(DateFormatUtil.getDefaultLocale()).getTime()});
			
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
	
	@SuppressWarnings("rawtypes")
	public static void save(Object object, Object dao) {
		
		Class objectClass = object.getClass();
		Class daoClass = dao.getClass();	
		
		prepareToSave(object);
		
		try {
			Method saveMethod = daoClass.getMethod("save", new Class[]{objectClass});
			saveMethod.invoke(dao, new Object[]{object});
		} 
		catch (SecurityException e) { 
			throw new RuntimeException(e.getMessage());
		}
		catch (NoSuchMethodException e) { 
			throw new RuntimeException(e.getMessage());
		}
		catch (IllegalArgumentException e) { 
			throw new RuntimeException(e.getMessage());
		}
		catch (IllegalAccessException e) { 
			throw new RuntimeException(e.getMessage());
		}
		catch (InvocationTargetException e) { 
			throw new RuntimeException(e.getMessage());
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void update(Object object, Object dao) {
		
		Class objectClass = object.getClass();
		Class daoClass = dao.getClass();	
		
		prepareToUpdate(object);
		
		try {		
			Method updateMethod = daoClass.getMethod("update", new Class[]{objectClass});			
			updateMethod.invoke(dao, new Object[]{object});
		} 
		catch (SecurityException e) { 
			throw new RuntimeException(e.getMessage());
		} 
		catch (NoSuchMethodException e) { 
			throw new RuntimeException(e.getMessage());
		} 
		catch (IllegalArgumentException e) { 
			throw new RuntimeException(e.getMessage());
		} 
		catch (IllegalAccessException e) { 
			throw new RuntimeException(e.getMessage());
		} 
		catch (InvocationTargetException e) { 
			throw new RuntimeException(e.getMessage());
		} 
	}
	
	@SuppressWarnings("rawtypes")
	public static void delete(Object object, Object dao){
		
		Class objectClass = object.getClass();
		Class daoClass = dao.getClass();	
		
		prepareToDelete(object);
		
		try {
			Method deleteMethod = daoClass.getMethod("delete", new Class[]{objectClass});
			deleteMethod.invoke(dao, new Object[]{object});
		} 
		catch (SecurityException e) { 
			throw new RuntimeException(e.getMessage());
		} 
		catch (NoSuchMethodException e) { 
			throw new RuntimeException(e.getMessage());
		} 
		catch (IllegalArgumentException e) { 
			throw new RuntimeException(e.getMessage());
		} 
		catch (IllegalAccessException e) { 
			throw new RuntimeException(e.getMessage());
		} 
		catch (InvocationTargetException e) { 
			throw new RuntimeException(e.getMessage());
		} 
	}
	
	@SuppressWarnings("rawtypes")
	public static Criteria getCriteria(Session session, Class clasz){
		Criteria criteria = session.createCriteria(clasz);
		criteria.add(Restrictions.eq("fdl", false));
		return criteria;
	}
	
	public static Criteria getCriteria(Criteria criteria, String property){
		Criteria cri = criteria.createCriteria(property);
		cri.add(Restrictions.eq("fdl", false));
		return cri;
	}
	
}