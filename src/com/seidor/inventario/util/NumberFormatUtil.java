package com.seidor.inventario.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

public class NumberFormatUtil {
	
	public static HashMap<String, String> currencyMap;
	
	public static BigDecimal toBigDecimal(Double d){
		BigDecimal bd = new BigDecimal(d, MathContext.DECIMAL64);
		bd = bd.setScale(2, RoundingMode.HALF_EVEN);
		return bd;
	}
	
	public static String format(BigDecimal n){
		if (n == null) return "";	
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.setMinimumFractionDigits(2);
		return nf.format(n);
	}
	
	public static String format(BigDecimal n, Integer d){
		if (n == null) return "";
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.setMaximumFractionDigits(d);
		nf.setMinimumFractionDigits(d);
		return nf.format(n);
	}
	
	public static String format(Double n, Integer d){
		if (n == null) return "";
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.setMaximumFractionDigits(d);
		nf.setMinimumFractionDigits(d);
		return nf.format(n);
	}
	
	public static String format(Float n, Integer d){
		if (n == null) return "";
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.setMaximumFractionDigits(d);
		nf.setMinimumFractionDigits(d);
		return nf.format(n);
	}
	
	public static String formatCurrency(Double n, Integer d, String currencyKey){
		if (n == null) return "";
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.setMaximumFractionDigits(d);
		nf.setMinimumFractionDigits(d);
		String ns = nf.format(n);
		String currency = currencyMap.get(currencyKey);
		if (currency != null){
			if (currency.equals("$")){
				ns = currency + ns;
			}
			else if (currency.equals("€")){
				ns += currency;
			}
		}
		return ns;
	}
	
	public static Double round(Double value, Integer places){
		if (places == null || places < 0) places = 0;
		
		BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_DOWN);
	    return bd.doubleValue();
	}
	
	public static String format(Double value, String format){
		if(value == null) return "";
		DecimalFormat df = new DecimalFormat(format);
		return df.format(value);
	}
	
	public static Double round(Double value){
		return round(value, 0);
	}
	
	static{
		currencyMap = new HashMap<String, String>();
		currencyMap.put("MXN", "$");
		currencyMap.put("USD", "$");
		currencyMap.put("EUR", "€");
	}
	
}
