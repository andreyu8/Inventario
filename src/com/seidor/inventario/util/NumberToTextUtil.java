package com.seidor.inventario.util;

public class NumberToTextUtil {
	
	private static int flag = 0;
	
	private static String unit(int num){
		switch (num){
		case 9:	return "nueve";
		case 8:	return "ocho";
		case 7:	return "siete";
		case 6:	return "seis";
		case 5:	return "cinco";
		case 4: return "cuatro";
		case 3: return "tres";
		case 2: return "dos";
		case 1:
				if (flag == 0)
					return "uno";
				else 
					return "un";
		case 0:
				return "";
		}
		return "";
	}
	
	private static String ten(int num) {
		String numAsString = "";
		if (num >= 90 && num <= 99) {
			numAsString += "noventa ";
			if (num > 90)
				numAsString += "y " + unit(num - 90);
		}
		else if (num >= 80 && num <= 89) {
			numAsString += "ochenta ";
			if (num > 80)
				numAsString += "y " + unit(num - 80);
		}
		else if (num >= 70 && num <= 79) {
			numAsString += "setenta ";
			if (num > 70)
				numAsString += "y " + unit(num - 70);
		}
		else if (num >= 60 && num <= 69) {
			numAsString += "sesenta ";
			if (num > 60)
				numAsString += "y " + unit(num - 60);
		}
		else if (num >= 50 && num <= 59) {
			numAsString += "cincuenta ";
			if (num > 50)
				numAsString += "y " + unit(num - 50);
		}
		else if (num >= 40 && num <= 49) {
			numAsString += "cuarenta ";
			if (num > 40)
				numAsString += "y " + unit(num - 40);
		}
		else if (num >= 30 && num <= 39) {
			numAsString += "treinta ";
			if (num > 30)
				numAsString += "y " + unit(num - 30);
		}
		else if (num >= 20 && num <= 29) {
			if (num == 20)
				numAsString += "veinte ";
			else
				numAsString += "veinti" + unit(num - 20);
		}
		else if (num >= 10 && num <= 19) {
			switch (num){
			case 10:
				numAsString += "diez ";
			case 11:
				numAsString += "once ";
			case 12:
				numAsString += "doce ";
			case 13:
				numAsString += "trece ";
			case 14:
				numAsString += "catorce ";
			case 15:
				numAsString += "quince ";
			case 16:
				numAsString += "dieciseis ";
			case 17:
				numAsString += "diecisiete ";
			case 18:
				numAsString += "dieciocho ";
			case 19:
				numAsString += "diecinueve ";
			}	
		}
		else
			numAsString += unit(num);
		return numAsString;
	}	

	private static String hundred(int num){
		String numAsString = "";
		if (num >= 100) {
			if (num >= 900 && num <= 999) {
				numAsString += "novecientos ";
				if (num > 900)
					numAsString += ten(num - 900);
			}
			else if (num >= 800 && num <= 899) {
				numAsString += "ochocientos ";
				if (num > 800)
					numAsString += ten(num - 800);
			}
			else if (num >= 700 && num <= 799) {
				numAsString += "setecientos ";
				if (num > 700)
					numAsString += ten(num - 700);
			}
			else if (num >= 600 && num <= 699) {
				numAsString += "seiscientos ";
				if (num > 600)
					numAsString += ten(num - 600);
			}
			else if (num >= 500 && num <= 599) {
				numAsString += "quinientos ";
				if (num > 500)
					numAsString += ten(num - 500);
			}
			else if (num >= 400 && num <= 499) {
				numAsString += "cuatrocientos ";
				if (num > 400)
					numAsString += ten(num - 400);
			}
			else if (num >= 300 && num <= 399) {
				numAsString += "trescientos ";
				if (num > 300)
					numAsString += ten(num - 300);
			}
			else if (num >= 200 && num <= 299) {
				numAsString += "doscientos ";
				if (num > 200)
					numAsString += ten(num - 200);
			}
			else if (num >= 100 && num <= 199) {
				if (num == 100)
					numAsString += "cien ";
				else
					numAsString += "ciento " + ten(num - 100);
			}
		}
		else
			numAsString += ten(num);
		
		return numAsString;	
	}	

	private static String thousands(int num){
		String numAsString = "";
		flag = 0;
		if (num >= 1000 && num < 2000) {
			numAsString += "mil " + hundred(num % 1000);
		}
		if (num >= 2000 && num < 10000) {
			flag = 1;
			numAsString += unit(num / 1000) + " mil ";
			flag = 0;
			numAsString += hundred(num % 1000);
		}
		if (num < 1000)
			numAsString += hundred(num);
		
		return numAsString;
	}		

	private static String tenthounsands(int num){
		String numAsString = "";
		if (num == 10000)
			numAsString += "diez mil";
		if (num > 10000 && num < 20000){
			flag = 1;
			numAsString += ten(num / 1000) + "mil ";
			flag = 0;
			numAsString += hundred(num % 1000);		
		}
		if (num >= 20000 && num < 100000){
			flag = 1;
			numAsString += ten(num / 1000) + " mil ";
			flag = 0;
			numAsString += thousands(num % 1000);		
		}
		if (num < 10000)
			numAsString += thousands(num);
		
		return numAsString;
	}		

	private static String hundredthounsands(int num){
		String numAsString = "";
		if (num == 100000)
			numAsString += "cien mil";
		if (num >= 100000 && num <1000000){
			flag = 1;
			numAsString += hundred(num / 1000) + " mil ";
			flag = 0;
			numAsString += hundred(num % 1000);		
		}
		if (num < 100000)
			numAsString += tenthounsands(num);
		return numAsString;
	}		

	private static String million(int num){
		String numAsString = "";
		if (num >= 1000000 && num < 2000000){
			flag = 1;
			numAsString += "Un millon " + hundredthounsands(num % 1000000);
		}
		if (num >= 2000000 && num < 10000000){
			flag = 1;
			numAsString += unit(num / 1000000) + " millones " + hundredthounsands(num % 1000000);
		}
		if (num < 1000000)
			numAsString += hundredthounsands(num);
		
		return numAsString;
	}		
	
	private static String tenmillion(int num){
		String numAsString = "";
		if (num == 10000000)
			numAsString += "diez millones";
		if (num > 10000000 && num < 20000000){
			flag = 1;
			numAsString += ten(num / 1000000) + "millones " + hundredthounsands(num % 1000000);		
		}
		if (num >= 20000000 && num < 100000000){
			flag = 1;
			numAsString += ten(num / 1000000) + " milllones " + million(num % 1000000);		
		}
		if (num < 10000000)
			numAsString += million(num);
		
		return numAsString;
	}		

	
	public static String convert(Integer num){
		flag = 0;
		return tenmillion(num);
	}
	
}