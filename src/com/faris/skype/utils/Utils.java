package com.faris.skype.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Utils {
	// Date
	private static GregorianCalendar calendar = null;
	private static final String strDateFormat = "[%s/%s/%s %s:%s:%s] ";

	public static String getDate() {
		if (calendar == null) calendar = (GregorianCalendar) Calendar.getInstance(Locale.UK);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int minuteOfHour = calendar.get(Calendar.MINUTE);
		int secondOfMinute = calendar.get(Calendar.SECOND);
		return String.format(strDateFormat, dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth, month < 10 ? "0" + month : month, calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY), minuteOfHour < 10 ? "0" + minuteOfHour : minuteOfHour, secondOfMinute < 10 ? "0" + secondOfMinute : secondOfMinute);
	}

	public static boolean isInteger(String aString) {
		try {
			Integer.parseInt(aString);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
}
