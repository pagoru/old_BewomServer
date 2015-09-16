package es.bewom.util;

import java.util.Calendar;

public class Datetime {

	public static String get(){
		Calendar d = Calendar.getInstance();
		return "[" + d.get(Calendar.DAY_OF_MONTH) + "/" + d.get(Calendar.MONTH) + "/" + d.get(Calendar.YEAR) + " " 
				+ d.get(Calendar.HOUR) + ":" + d.get(Calendar.MINUTE) + ":" + d.get(Calendar.SECOND) + "] ";
	}
	
}
