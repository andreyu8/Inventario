package com.seidor.inventario.util;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.zkoss.zk.ui.event.Events;

public class StringUtil {
	
	private static ArrayList<String> ZKEVENTS = new ArrayList<String>();
	
	static {
		ZKEVENTS.add(Events.ON_AFTER_SIZE);
		ZKEVENTS.add(Events.ON_BLUR);
		ZKEVENTS.add(Events.ON_BOOKMARK_CHANGE);
		ZKEVENTS.add(Events.ON_CANCEL);
		ZKEVENTS.add(Events.ON_CHANGE);
		ZKEVENTS.add(Events.ON_CHANGING);
		ZKEVENTS.add(Events.ON_CHECK);
		ZKEVENTS.add(Events.ON_CLICK);
		ZKEVENTS.add(Events.ON_CLIENT_INFO);
		ZKEVENTS.add(Events.ON_CLOSE);
		ZKEVENTS.add(Events.ON_CREATE);
		ZKEVENTS.add(Events.ON_CTRL_KEY);
		ZKEVENTS.add(Events.ON_DESKTOP_RECYCLE);
		ZKEVENTS.add(Events.ON_DOUBLE_CLICK);
		ZKEVENTS.add(Events.ON_DROP);
		ZKEVENTS.add(Events.ON_ERROR);
		ZKEVENTS.add(Events.ON_FOCUS);
		ZKEVENTS.add(Events.ON_FULFILL);
		ZKEVENTS.add(Events.ON_GROUP);
		ZKEVENTS.add(Events.ON_MAXIMIZE);
		ZKEVENTS.add(Events.ON_MINIMIZE);
		ZKEVENTS.add(Events.ON_MODAL);
		ZKEVENTS.add(Events.ON_MOUSE_OUT);
		ZKEVENTS.add(Events.ON_MOUSE_OVER);
		ZKEVENTS.add(Events.ON_MOVE);
		ZKEVENTS.add(Events.ON_NOTIFY);
		ZKEVENTS.add(Events.ON_OK);
		ZKEVENTS.add(Events.ON_OPEN);
		ZKEVENTS.add(Events.ON_PIGGYBACK);
		ZKEVENTS.add(Events.ON_RENDER);
		ZKEVENTS.add(Events.ON_RIGHT_CLICK);
		ZKEVENTS.add(Events.ON_SCROLL);
		ZKEVENTS.add(Events.ON_SCROLLING);
		ZKEVENTS.add(Events.ON_SELECT);
		ZKEVENTS.add(Events.ON_SELECTION);
		ZKEVENTS.add(Events.ON_SIZE);
		ZKEVENTS.add(Events.ON_SORT);
		ZKEVENTS.add(Events.ON_STUB);
		ZKEVENTS.add(Events.ON_SWIPE);
		ZKEVENTS.add(Events.ON_TIMER);
		ZKEVENTS.add(Events.ON_UNGROUP);
		ZKEVENTS.add(Events.ON_UPLOAD);
		ZKEVENTS.add(Events.ON_URI_CHANGE);
		ZKEVENTS.add(Events.ON_USER);
		ZKEVENTS.add(Events.ON_VISIBILITY_CHANGE);
		ZKEVENTS.add(Events.ON_Z_INDEX);
	}
	
	public static String capitalize(String str) {
		if (str != null) {
			if (str.length() > 1) {
				str = str.substring(0, 1).toUpperCase() + str.substring(1);
			}
			else {
				str = str.toUpperCase();
			}
			return str;
		}
		return null;
	}
	
	public static String takeOffExtraSpaces(String str) {
		return str.trim().replaceAll(" +", " ");
	}
	
	public static String takeOffSpecialChars(String str) {
		return str.replaceAll("[^a-zA-Z0-9Ã±Ã‘ ]", "");
	}
	
	public static String replaceAccents(String str) {
		String res;
		
		res = str.replace("Ã¡", "a");
		res = res.replace("Ã©", "e");
		res = res.replace("Ã­", "i");
		res = res.replace("Ã³", "o");
		res = res.replace("Ãº", "u");
		
		res = res.replace("Ã�", "A");
		res = res.replace("Ã‰", "E");
		res = res.replace("Ã�", "I");
		res = res.replace("Ã“", "O");
		res = res.replace("Ãš", "U");
		
		return res;
	}
	
	public static String formatWithOutSpecialCharsAndExtraSpaces(String str) {
		return takeOffSpecialChars(replaceAccents(takeOffExtraSpaces(str)));
	}
	
	public static String getAttributeName(String fieldName){
		if (fieldName.contains("_")) {
			String[] parts = fieldName.split("_");
			String attributeName = parts[0];
			Integer size = parts.length;
			for (int i = 1; i < size; i++){
				attributeName += capitalize(parts[i]);
			}
			return attributeName;
		}
		else {
			return fieldName;
		}
	}
	
	public static String remove(String str, String begin, String end){
		try {
			if (str.contains(begin) && str.contains(end)) {
				int beginI = str.indexOf(begin);
				int endI = str.indexOf(end, beginI + begin.length() + 1);
				if (beginI > -1 && endI > -1 && beginI < endI){
					String remove = str.substring(beginI, endI + end.length());
					return str.replace(remove, " ");
				}
			}
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return str;
	}
	
	
	
	
	
	public static String parseFormatDate(Object date, String format, String newformat){
		String value = null; 
		if(date!=null) value= date.toString(); else return "";
		if (format == null) format = "yyyy-MM-dd HH:mm:ss.S";
		SimpleDateFormat defaultFormat = new SimpleDateFormat(format);
		
		if (newformat == null) newformat = "dd/MM/yyyy";
		SimpleDateFormat newFormat = new SimpleDateFormat(newformat);

		String dates = null;
		try {
		    dates = newFormat.format(defaultFormat.parse(value));
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		return dates;
	}
	
	public static String fistLetterUpper(String s) {
		String mayuscula = s.charAt(0) + "";
		mayuscula = mayuscula.toUpperCase();
		s = s.replaceFirst(s.charAt(0) + "", mayuscula);
		return s;
	}
	
	public static String fillWithZeros(Integer i, Integer l) {
		if (i != null) {
			if (i >= 0) {
				return fillWithZeros(i + "", l);
			}
			else {
				i = i * -1;
				return "-" + fillWithZeros(i + "", l);
			}
		}
		return null;
	}
	
	public static String fillWithZeros(String i, Integer l) {
		if (i != null) {
			while (i.length() < l) i = "0" + i;
		}
		return i;
	}
	
	public static String getColorShade(String hex) {
		Color color = Color.decode(hex);
		String shade = String.format("#%02x%02x%02x", (int)(color.getRed() * .5), (int)(color.getGreen() * .5), (int)(color.getBlue() * .5));
		return shade;
	}
	
	public static String replace(HashMap<String, Object> params, String str){
		if (str != null && params != null){ 
			for (Object parameter : params.keySet()) {
				if (parameter == null) parameter = "";
				if (parameter instanceof String) {
					String param = (String)parameter;
					str = str.replace(param, convert(params.get(param)));
				}
			}
		}
		return str;
	}
	
	public static String convert(Object value){
		return "";
	}
	
	
	
	
	public static String getComboValWithoutCode(String valS){
		//Eliminate the code part 
		if (valS != null && valS.contains("#")){
			Integer ifh = valS.indexOf("#");
			if (ifh >= 0) {
				return valS.substring(0, ifh);
			}
		}
		
		return valS;
	}
	
	public static Integer getComboValOnlyCode(String valS){
		//Eliminate the code part 
		if (valS != null && valS.contains("#")){
			Integer ifh = valS.indexOf("#");
			if (ifh >= 0) {
				try {
					return Integer.valueOf(valS.substring(ifh + 1));
				} catch (Exception ex){
					//If any error interpreting the code
					ex.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	public static String removeAll(String str, String begin, String end){
		do {
			str = remove(str, begin, end);
		} while (str.contains(begin));
		return str;
	}
	
	public static String removeAllEvents(String form){
		for (String event : ZKEVENTS){
			form = StringUtil.removeAll(form, event + "=\"", "\"");
			form = StringUtil.removeAll(form, event + "='", "'");
		}
		return form;
	}
	
	public static String getFormat(String filename){
		String format = "";
		Integer index = filename.lastIndexOf(".");
		format = filename.substring(index + 1);
		return format;
	}
	
	public static String getFilename(String filename){
		String name = "";
		Integer index = filename.lastIndexOf(".");
		if (index >= 0) {
			name = filename.substring(0, index);
		}
		return name;
	}
	
	public static Boolean isConsecutive(String code){
		Integer prev = null;
		for(int i = 0; i < code.length(); i++){
			Integer num = Integer.valueOf(code.charAt(i));
			if (prev != null) {
				if (num.equals(prev + 1) || num.equals(prev -1)){
					return true;
				}
			}
			prev = num;
		}
		return false;
	}
	
	public static Boolean isReapeated(String code, Integer limit){
		HashMap<Character, Integer> numbers = new HashMap<Character, Integer>();
		
		for(int i = 0; i < code.length(); i++){
			Character c = code.charAt(i);
			Integer num = numbers.get(c);
			if (num == null) num = 0;
			
			numbers.put(c, num + 1);
			
			if (num + 1 > limit){
				return true;
			}
		}
		return false;
	}
	
	
}