package es.bewom.util;

import java.sql.Timestamp;
import java.util.Date;

public class Datetime {

	public static String get(){
		Date date = new Date();
		Timestamp d = new Timestamp(date.getTime());
		return "[" + d.toString().substring(0, d.toString().length() - 4).replace("-", "/") + "] ";
	}
	
}
