package br.com.caelum.jdbc.util;

import java.sql.Date;
import java.util.Calendar;

public class DateUtil {
	
	public static Date toSQLDate(Calendar data) {
		return new Date(data.getTimeInMillis());
	}
	
	public static Calendar toCalendar(Date data) {
		Calendar date = Calendar.getInstance();
		date.setTime(data);
		return date;
	}
}
