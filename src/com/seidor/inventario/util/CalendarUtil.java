package com.seidor.inventario.util;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

	public static Date removeTime(Date date) {	
		Calendar cal = Calendar.getInstance();  
		cal.setTime(date);  
		removeTime(cal);
		return cal.getTime();
	}
	
	public static void removeTime(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);  
		cal.set(Calendar.MINUTE, 0);  
		cal.set(Calendar.SECOND, 0);  
		cal.set(Calendar.MILLISECOND, 0);   
	}
	
	public static Date setTime(Date date, Integer hour) {	
		Calendar cal = Calendar.getInstance();  
		cal.setTime(date);  
		setTime(cal, hour);
		return cal.getTime();
	}
	
	public static void setTime(Calendar cal, Integer hour) {
		cal.set(Calendar.HOUR_OF_DAY, hour);  
		cal.set(Calendar.MINUTE, 0);  
		cal.set(Calendar.SECOND, 0);  
		cal.set(Calendar.MILLISECOND, 0); 
	}
	
}