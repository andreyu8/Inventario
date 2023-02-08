package com.seidor.inventario.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.zkoss.util.Locales;

public class DateFormatUtil {
	public static Integer BUSINESS_DAYS_HRS = 8;
	
	public static Locale getDefaultLocale(){
		return new Locale("es", "MX");
	}
	
	public static Integer getBusinessDaysBetween(Date d1, Date d2) {
		Integer diffDays = 0;
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		while (c1.before(c2) || c1.equals(c2)) {
			if (c1.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
					|| c1.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
				diffDays++;
			}
			c1.add(Calendar.DATE, 1);
		}
		return diffDays;
	}
	
	public static Integer daysBetween(Date d1, Date d2){
		if (d1 != null && d2 != null) {
			return (int)((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
		}
		return 0;
	}
	
	public static Integer minutesBetween(Date d1, Date d2) {
		if (d1 != null && d2 != null) {
			return (int)((d2.getTime() - d1.getTime()) / (1000 * 60));
		}
		return 0;
	}
	
	public static Integer secondsBetween(Date d1, Date d2) {
		if (d1 != null && d2 != null) {
			return (int)((d2.getTime() - d1.getTime()) / (1000));
		}
		return 0;
	}
	
	public static Date parseDate(String date, String format){
		if (format == null) format = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date dates = null;
		try {
			dates = sdf.parse(date);
		} catch (ParseException ex) { ex.printStackTrace(); }
		return dates;
	}
	
	public static void resetTime(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
	}
	
	public static void resetTimeUp(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
	}
	
	public static String getFormatedWeek(Date date){
		if (date == null) return "";
		String weekAsString = "";
		Calendar calendar = Calendar.getInstance(Locales.getCurrent());
		calendar.setTime(date);
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
			calendar.add(Calendar.DAY_OF_YEAR, -1);
		}
		weekAsString += calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DAY_OF_YEAR, 6);
		weekAsString += " al ";
		weekAsString += calendar.get(Calendar.DAY_OF_MONTH);
		weekAsString += " de ";
		weekAsString += getCompleteMonth(calendar.get(Calendar.MONTH));
		weekAsString += " de ";
		weekAsString += calendar.get(Calendar.YEAR);
		return weekAsString;
	}
	
	public static String getDayOfWeekFormatedDate(Date date, Boolean hour){
		if (date == null) return "";
		String dateAsString = "";
		Calendar calendar = Calendar.getInstance(Locales.getCurrent());
		calendar.setTime(date);
		dateAsString += getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
		dateAsString += ", " + calendar.get(Calendar.DAY_OF_MONTH);
		dateAsString += " de ";
		dateAsString += getCompleteMonth(calendar.get(Calendar.MONTH));
		dateAsString += " de ";
		dateAsString += calendar.get(Calendar.YEAR);
		if (hour) {
			dateAsString += " ";
			dateAsString += getHour(date);
		}
		return dateAsString;
	}
	
	public static String getLongFormatedDate(Date date, Boolean hour){
		if (date == null) return "";
		String dateAsString = "";
		Calendar calendar = Calendar.getInstance(Locales.getCurrent());
		calendar.setTime(date);
		dateAsString += calendar.get(Calendar.DAY_OF_MONTH);
		dateAsString += " de ";
		dateAsString += getCompleteMonth(calendar.get(Calendar.MONTH));
		dateAsString += " de ";
		dateAsString += calendar.get(Calendar.YEAR);
		if (hour) {
			dateAsString += " ";
			dateAsString += getHour(date);
		}
		return dateAsString;
	}
	
	public static String getFormatedDate(Date date, Boolean hour){
		if (date == null) return "";
		String dateAsString = "";
		Calendar calendar = Calendar.getInstance(Locales.getCurrent());
		calendar.setTime(date);
		dateAsString += getDayNum(calendar.get(Calendar.DAY_OF_MONTH));
		dateAsString += "-";
		dateAsString += getMonth(calendar.get(Calendar.MONTH));
		dateAsString += "-";
		dateAsString += calendar.get(Calendar.YEAR);
		if (hour) {
			dateAsString += " ";
			dateAsString += getHour(date);
		}
		return dateAsString;
	}
	
	public static String getXMLFormatedDate(Date date){
		if (date == null) return "";
		String dateAsString = "";
		Calendar calendar = Calendar.getInstance(Locales.getCurrent());
		calendar.setTime(date);
		dateAsString += calendar.get(Calendar.YEAR);
		dateAsString += "-";
		dateAsString += getMonthNum(calendar.get(Calendar.MONTH));
		dateAsString += "-";
		dateAsString += getDayNum(calendar.get(Calendar.DAY_OF_MONTH));
		dateAsString += "T";
		dateAsString += getLongHour(date);
		return dateAsString;
	}
	
	public static String getSimpleFormatedDate(Date date, Boolean longYear){
		if (date == null) return "";
		Calendar calendar = Calendar.getInstance(Locales.getCurrent());
		calendar.setTime(date);
		String dateAsString = getDayNum(calendar.get(Calendar.DAY_OF_MONTH)) + "/" 
								 + getMonthNum(calendar.get(Calendar.MONTH)) + "/";
		if (longYear)
			dateAsString += calendar.get(Calendar.YEAR);
		else
			dateAsString += getYear(calendar.get(Calendar.YEAR));
		return dateAsString;
	}
	
	public static String getShortFormatedDate(Date date, Boolean hour){
		if (date == null) return "";
		Calendar calendar = Calendar.getInstance(Locales.getCurrent());
		calendar.setTime(date);
		String dateAsString = getDayNum(calendar.get(Calendar.DAY_OF_MONTH)) + "-" 
								 + getMonth(calendar.get(Calendar.MONTH));
		if (hour) {
			dateAsString += " ";
			dateAsString += getHour(date);
		}
		return dateAsString;
	}
	
	public static String getOnlyHour(Date date){
		if (date == null) return "";
		Calendar calendar = Calendar.getInstance(Locales.getCurrent());
		calendar.setTime(date);
		String dateAsString = "";
		Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour < 10) dateAsString += 0; 
		dateAsString += hour;
		return dateAsString;
	}
	
	public static String getOnlyHourS(Date date, Boolean uppercase){
		if (date == null) return "";
		Calendar calendar = Calendar.getInstance(Locales.getCurrent());
		calendar.setTime(date);
		Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
		String dateAsString = getOnlyHourS(hour);
		if (uppercase == null) return dateAsString;
		else if (uppercase) return dateAsString.toUpperCase();
		else return dateAsString.toLowerCase();
	}
	
	private static String getOnlyHourS(Integer hour) {
		if (Integer.valueOf(0).equals(hour)) return "Cero";
		else if (Integer.valueOf(0).equals(hour)) return "Una";
		else if (hour <= 23) return StringUtil.fistLetterUpper(NumberToTextUtil.convert(hour));
		else return "-";
	}
	
	public static String getMinutes(Date date){
		if (date == null) return "";
		Calendar calendar = Calendar.getInstance(Locales.getCurrent());
		calendar.setTime(date);
		String dateAsString = "";
		Integer minutes = calendar.get(Calendar.MINUTE);
		if (minutes < 10) dateAsString += 0; 
		dateAsString += minutes;
		return dateAsString;
	}
	
	public static String getMinutesS(Date date, Boolean uppercase){
		if (date == null) return "";
		Calendar calendar = Calendar.getInstance(Locales.getCurrent());
		calendar.setTime(date);
		Integer minutes = calendar.get(Calendar.MINUTE);
		String dateAsString = getMinutesS(minutes);
		if (uppercase == null) return dateAsString;
		else if (uppercase) return dateAsString.toUpperCase();
		else return dateAsString.toLowerCase();
	}
	
	private static String getMinutesS(Integer min) {
		if (Integer.valueOf(0).equals(min)) return "Cero";
		else if (Integer.valueOf(0).equals(min)) return "Una";
		else if (min <= 60) return StringUtil.fistLetterUpper(NumberToTextUtil.convert(min));
		else return "-";
	}
	
	public static String getHour(Date date){
		if (date == null) return "";
		Calendar calendar = Calendar.getInstance(Locales.getCurrent());
		calendar.setTime(date);
		String dateAsString = "";
		Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
		Integer minutes = calendar.get(Calendar.MINUTE);
		if (hour < 10) dateAsString += 0; 
		dateAsString += hour;
		dateAsString += ":";
		if (minutes < 10) dateAsString += 0; 
		dateAsString += minutes;
		return dateAsString;
	}
	
	public static String getLongHour(Date date){
		if (date == null) return "";
		Calendar calendar = Calendar.getInstance(Locales.getCurrent());
		calendar.setTime(date);
		String dateAsString = "";
		Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
		Integer minutes = calendar.get(Calendar.MINUTE);
		Integer seconds = calendar.get(Calendar.SECOND);
		if (hour < 10) dateAsString += 0; 
		dateAsString += hour;
		dateAsString += ":";
		if (minutes < 10) dateAsString += 0; 
		dateAsString += minutes;
		dateAsString += ":";
		if (seconds < 10) dateAsString += 0;
		dateAsString += seconds;
		return dateAsString;
	}
	
	public static Date getCurrentWeekDate(Date date){
		Calendar today = Calendar.getInstance(Locales.getCurrent());
		Calendar caldate = Calendar.getInstance(Locales.getCurrent());
		caldate.setTime(date);
		Integer todayDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
		Integer dateDayOfWeek = caldate.get(Calendar.DAY_OF_WEEK);
		if (todayDayOfWeek < dateDayOfWeek){
			today.add(Calendar.DAY_OF_YEAR, 1);
			while (todayDayOfWeek != dateDayOfWeek){
				today.add(Calendar.DAY_OF_YEAR, 1);
				todayDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
			}
		}
		else if (todayDayOfWeek > dateDayOfWeek){
			today.add(Calendar.DAY_OF_YEAR, -1);
			while (todayDayOfWeek != dateDayOfWeek){
				today.add(Calendar.DAY_OF_YEAR, -1);
				todayDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
			}
		}
		today.set(Calendar.HOUR_OF_DAY, caldate.get(Calendar.HOUR_OF_DAY));
		today.set(Calendar.MINUTE, caldate.get(Calendar.MINUTE));
		today.set(Calendar.SECOND, caldate.get(Calendar.SECOND));
		today.set(Calendar.MILLISECOND, caldate.get(Calendar.MILLISECOND));
		return today.getTime();
	}
	
	public static String getCompleteMonth(Date date){
		if (date == null) return "";
		Calendar cal = Calendar.getInstance(getDefaultLocale());
		cal.setTime(date);
		return getCompleteMonth(cal.get(Calendar.MONTH));
	}
	
	public static String getCompleteMonth(Integer month){
		if (month == null) return "";
		switch (month){
			case Calendar.JANUARY: return "Enero";
			case Calendar.FEBRUARY: return "Febrero";
			case Calendar.MARCH: return "Marzo";
			case Calendar.APRIL: return "Abril";
			case Calendar.MAY: return "Mayo";
			case Calendar.JUNE: return "Junio";
			case Calendar.JULY: return "Julio";
			case Calendar.AUGUST: return "Agosto";
			case Calendar.SEPTEMBER: return "Septiembre";
			case Calendar.OCTOBER: return "Octubre";
			case Calendar.NOVEMBER: return "Noviembre";
			case Calendar.DECEMBER: return "Diciembre";
			default: return "";
		}
	}
	
	public static String getMonth(Date date){
		if (date == null) return "";
		Calendar cal = Calendar.getInstance(getDefaultLocale());
		cal.setTime(date);
		return getMonth(cal.get(Calendar.MONTH));
	}
	
	public static String getMonth(Integer month){
		switch (month){
			case Calendar.JANUARY: return "ene";
			case Calendar.FEBRUARY: return "feb";
			case Calendar.MARCH: return "mar";
			case Calendar.APRIL: return "abr";
			case Calendar.MAY: return "may";
			case Calendar.JUNE: return "jun";
			case Calendar.JULY: return "jul";
			case Calendar.AUGUST: return "ago";
			case Calendar.SEPTEMBER: return "sep";
			case Calendar.OCTOBER: return "oct";
			case Calendar.NOVEMBER: return "nov";
			case Calendar.DECEMBER: return "dec";
			default: return null;
		}
	}
	
	public static String getDayOfWeek(Date date){
		if (date == null) return "";
		Calendar cal = Calendar.getInstance(getDefaultLocale());
		cal.setTime(date);
		return getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));
	}
	
	public static String getDayOfWeek(Integer weekday){
		if (weekday == null) return "";
		switch (weekday){
			case Calendar.SUNDAY: return "Domingo";
			case Calendar.MONDAY: return "Lunes";
			case Calendar.TUESDAY: return "Martes";
			case Calendar.WEDNESDAY: return "Miércoles";
			case Calendar.THURSDAY: return "Jueves";
			case Calendar.FRIDAY: return "Viernes";
			case Calendar.SATURDAY: return "Sabado";
			default: return "";
		}
	}
	
	public static String getMonthNum(Date date){
		if (date == null) return "";
		Calendar cal = Calendar.getInstance(getDefaultLocale());
		cal.setTime(date);
		return getMonthNum(cal.get(Calendar.MONTH));
	}
	
	private static String getMonthNum(Integer month){
		switch (month){
			case 0: return "01";
			case 1: return "02";
			case 2: return "03";
			case 3: return "04";
			case 4: return "05";
			case 5: return "06";
			case 6: return "07";
			case 7: return "08";
			case 8: return "09";
			case 9: return "10";
			case 10: return "11";
			case 11: return "12";
			default: return null;
		}
	}
	
	public static String getDayNum(Date date){
		if (date == null) return "";
		Calendar cal = Calendar.getInstance(getDefaultLocale());
		cal.setTime(date);
		return getDayNum(cal.get(Calendar.DAY_OF_MONTH));
	}
	
	public static String getDay(Date date){
		if (date == null) return "";
		Calendar cal = Calendar.getInstance(getDefaultLocale());
		cal.setTime(date);
		return getDay(cal.get(Calendar.DAY_OF_MONTH));
	}
	
	public static String getUpperCaseDay(Date date){
		if (date == null) return "";
		Calendar cal = Calendar.getInstance(getDefaultLocale());
		cal.setTime(date);
		return getDay(cal.get(Calendar.DAY_OF_MONTH)).toUpperCase();
	}
	
	public static String getLowerCaseDay(Date date){
		if (date == null) return "";
		Calendar cal = Calendar.getInstance(getDefaultLocale());
		cal.setTime(date);
		return getDay(cal.get(Calendar.DAY_OF_MONTH)).toLowerCase();
	}
	
	private static String getDay(Integer day){
		switch (day) {
			case 1: return "Primero";
			case 2: return "Dos";
			case 3: return "Tres";
			case 4: return "Cuatro";
			case 5: return "Cinco";
			case 6: return "Seis";
			case 7: return "Siete";
			case 8: return "Ocho";
			case 9: return "Nueve";
			case 10: return "Diez";
			case 11: return "Once";
			case 12: return "Doce";
			case 13: return "Trece";
			case 14: return "Catorce";
			case 15: return "Quince";
			case 16: return "Dieciséis";
			case 17: return "Diecisiete";
			case 18: return "Dieciocho";
			case 19: return "Diecinueve";
			case 20: return "Viente";
			case 21: return "Veintiuno";
			case 22: return "Veintidos";
			case 23: return "Veintitrés";
			case 24: return "Vienticuatro";
			case 25: return "Vienticinco";
			case 26: return "Veintiséis";
			case 27: return "Ventisiete";
			case 28: return "Ventiocho";
			case 29: return "Ventinueve";
			case 30: return "Treinta";
			case 31: return "Treintaiuno";
			default: return null;
		}
	}
	
	private static String getDayNum(Integer day){
		if (day < 10)
		switch (day){
			case 1: return "01";
			case 2: return "02";
			case 3: return "03";
			case 4: return "04";
			case 5: return "05";
			case 6: return "06";
			case 7: return "07";
			case 8: return "08";
			case 9: return "09";
			default: return null;
		}
		else {
			return String.valueOf(day);
		}
	}
	
	public static String getYear(Date date){
		if (date == null) return "";
		Calendar cal = Calendar.getInstance(getDefaultLocale());
		cal.setTime(date);
		return getYear(cal.get(Calendar.YEAR));
	}
	
	private static String getYear(Integer year){
		if (year == null) return "";
		String yearS = String.valueOf(year);
		return yearS.substring(2);
	}
	
	public static String getCompleteYear(Date date){
		if (date == null) return "";
		Calendar cal = Calendar.getInstance(getDefaultLocale());
		cal.setTime(date);
		return getCompleteYear(cal.get(Calendar.YEAR));
	}
	
	private static String getCompleteYear(Integer year){
		if (year == null) return "";
		return String.valueOf(year);
	}
	
	public static String getYearS(Date date, Boolean uppercase) {
		if (date == null) return "";
		Calendar cal = Calendar.getInstance(getDefaultLocale());
		cal.setTime(date);
		String yearS = NumberToTextUtil.convert(cal.get(Calendar.YEAR));
		if (uppercase != null && uppercase) return yearS.toUpperCase();
		else if (uppercase != null && !uppercase) return yearS.toLowerCase();
		else return yearS;
	}
	
	public static String getUpperCaseCompleteMonth(Date date){
		if (date == null) return "";
		Calendar cal = Calendar.getInstance(getDefaultLocale());
		cal.setTime(date);
		return getCompleteMonth(cal.get(Calendar.MONTH)).toUpperCase();
	}
	
	public static String getLowerCaseCompleteMonth(Date date){
		if (date == null) return "";
		Calendar cal = Calendar.getInstance(getDefaultLocale());
		cal.setTime(date);
		return getCompleteMonth(cal.get(Calendar.MONTH)).toLowerCase();
	}
	
	public static String getFormatedTime(Double hours) {		
		
		if (hours == 0) {
			return " 0 horas";
		}
		
		long iPart;
		double fPart;
		
		if (hours > 0) {
			iPart = (long) hours.doubleValue();
			fPart = hours - iPart;
		} else {
			iPart = (long) (hours.doubleValue()*-1);
			fPart = (hours*-1) - iPart;
		}
		
		String formatedTime= "";
		
		if(iPart>BUSINESS_DAYS_HRS) {
			long iDay = iPart/BUSINESS_DAYS_HRS; 
			formatedTime = iDay + " d ";
			iPart = iPart%BUSINESS_DAYS_HRS;
		}
		
		formatedTime += iPart + " hrs";

		if (fPart > 0) { 
			Double minutes = fPart * 60;
			
			formatedTime += " " + minutes.intValue() + " min";
		}
		
		return formatedTime;
	}
        
    /**
     * Converts a Date to String representation given a format string. If no
     * pattern is given it takes a default one. If date is null, it takes new
     * Date()
     *
     * @param date
     * @param format
     * @return a formatted String representing a date.
     */
    public static String convertDateToFormattedString(final Date date, final String format) {
        String defaultFormat = format == null || format.isEmpty() ? "dd/MM/yyyy" : format;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(defaultFormat);
        Instant instant = date == null? new Date().toInstant(): date.toInstant();
        LocalDateTime ldt = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return ldt.format(formatter);
    }
	
}