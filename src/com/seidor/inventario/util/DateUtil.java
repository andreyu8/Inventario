package com.seidor.inventario.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static Date toStartOfYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, Calendar.JANUARY, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	public static Date toEndOfYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year+1);
		calendar.set(Calendar.DAY_OF_YEAR, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND,0);
		return calendar.getTime();
	}

	public static int daysBetween(Date startDate, Date endDate) {
		Calendar dayOne = Calendar.getInstance(),
				dayTwo =  Calendar.getInstance();
		dayOne.setTime(startDate);
		dayTwo.setTime(endDate);
		if (dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR)) {
			return Math.abs(dayOne.get(Calendar.DAY_OF_YEAR) - dayTwo.get(Calendar.DAY_OF_YEAR));
		} else {
			if (dayTwo.get(Calendar.YEAR) > dayOne.get(Calendar.YEAR)) {
				Calendar temp = dayOne;
				dayOne = dayTwo;
				dayTwo = temp;
			}
			int extraDays = 0;

			int dayOneOriginalYearDays = dayOne.get(Calendar.DAY_OF_YEAR);

			while (dayOne.get(Calendar.YEAR) > dayTwo.get(Calendar.YEAR)) {
				dayOne.add(Calendar.YEAR, -1);
				extraDays += dayOne.getActualMaximum(Calendar.DAY_OF_YEAR);
			}

			return extraDays - dayTwo.get(Calendar.DAY_OF_YEAR) + dayOneOriginalYearDays ;
		}
	}
	

	public static int monthsBetween(Date startDate, Date endDate) {
		Calendar monthOne = Calendar.getInstance(),
				monthTwo = Calendar.getInstance();
		monthOne.setTime(startDate);
		monthTwo.setTime(endDate);

		int diff = 0;
		if(monthTwo.after(monthOne)) {
			System.out.println("monthOne.after(monthTwo): " + monthOne.after(monthTwo));
			while(monthTwo.after(monthOne)) {
				monthOne.add(Calendar.MONTH, 1);
				diff++;
			}
		} else if(monthTwo.before(monthOne)) {
			System.out.println("monthOne.before(monthTwo): " + monthOne.before(monthTwo));
			while(monthTwo.before(monthOne)) {
				monthOne.add(Calendar.MONTH, -1);
				diff--;
			}
		}
		return diff;
	}
	
	public static int dayOfMonth(Date date){
		Calendar day = Calendar.getInstance();
		day.setTime(date);

		return day.get(Calendar.DAY_OF_MONTH);
	}

	public static int daysOfMonth(Date date){
		Calendar day = Calendar.getInstance();
		day.setTime(date);
		
		return day.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static Date nextDayOfWeek(int dayOfWeek) {
		Calendar date = Calendar.getInstance();
		int diff = dayOfWeek - date.get(Calendar.DAY_OF_WEEK);
		if (!(diff > 0)) {
			diff += 7;
		}
		date.add(Calendar.DAY_OF_MONTH, diff);
		return date.getTime();
	}
	
	public static Date dayOfThisWeek(int dayOfWeek) {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.AM_PM, Calendar.AM);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.HOUR, 0);
		if(date.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
			date.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		}
		return date.getTime();
	}

	public static int daysInYear(int year) {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, year);
		return date.getActualMaximum(Calendar.DAY_OF_YEAR);
	}
	
	public static int daysInYear(Calendar calendar) {
		return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
	}
	
	public static int daysInMonth(int month) {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.MONTH, month);
		return date.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public static int daysInMonth(Calendar calendar) {
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public static int currentYear() {
		Calendar date = Calendar.getInstance();
		return date.get(Calendar.YEAR);
	}
	
	public static Integer convertDateTotalDays (Date dateCurrent) {
		SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateBeforeString = "30/12/1899";
		float totalDays = 0;
		try {
			Date dateBefore = myFormat.parse(dateBeforeString);
			long difference = dateCurrent.getTime() - dateBefore.getTime();
			totalDays = (difference / (1000*60*60*24));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return  Math.round(totalDays);
	}
	
	public static String dateMax (ArrayList<Date> dates) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Date currentDate = null;
		Boolean compareMaxDate = false;  
		for (Date d : dates) {
			
			if (currentDate != null) {
				compareMaxDate =d.after(currentDate);
			} else {
				currentDate = d;
			}			
			if (compareMaxDate) {
				currentDate = d;
			} 
		}
		return currentDate == null ? "" : sdf.format(currentDate);
	}
	
	
	public static Date getOnlyDate(Date dateCurrent) {
		if (dateCurrent != null) {
			Calendar date = Calendar.getInstance();
			
			date.setTime(dateCurrent);
			date.set(Calendar.HOUR_OF_DAY, 0);
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.SECOND, 0);
			date.set(Calendar.MILLISECOND, 0);
			
			return date.getTime();
		} return null;
	}
	
	public static Date getTomorrow(Date dateCurrent) {
		if (dateCurrent != null) {
			Calendar date = Calendar.getInstance();
			
			date.setTime(dateCurrent);
			date.set(Calendar.HOUR_OF_DAY, 0);
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.SECOND, 0);
			date.set(Calendar.MILLISECOND, 0);
			date.add(Calendar.DAY_OF_YEAR, 1);
			
			return date.getTime();
		} return null;
	}
	
	public static Integer getHour(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.HOUR_OF_DAY);
		} return null;
	}
	
	public static Integer getYear(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.YEAR);
		} return null;
	}
	  
}