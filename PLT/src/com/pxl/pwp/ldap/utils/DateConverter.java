package com.pxl.pwp.ldap.utils;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateConverter {

	public String odiToDateConvert(String oidTimeStamp) {

		// Create a DateFormatter object for displaying date information.
		DateFormat formatter = new SimpleDateFormat(
				"MMM dd yyyy hh:mm:ss.SSS zzz");

		// Setting TIMEZONE to GMT
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

		final String secondsInHourStr = (60 * 60) + "";

		// Convert the huge number to hours
		BigInteger big = new BigInteger(oidTimeStamp);

		// Note, I got the spitByValue after trial/error testing, hope its
		// correct
		String splitByValue = "10000000";
		BigInteger secondsBig = big.divide(new BigInteger(splitByValue));

		BigInteger hoursBig = secondsBig
				.divide(new BigInteger(secondsInHourStr));

		// Get the remaining seconds
		BigInteger modSeconds = secondsBig
				.mod(new BigInteger(secondsInHourStr));
		// Create a Calender object representing 1601-01-01 00:00:01
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1601);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR, -12);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 1);

		// Add the hours
		cal.add(Calendar.HOUR, hoursBig.intValue());

		// Add seconds
		cal.add(Calendar.SECOND, modSeconds.intValue());

		// Here is your Java object
		Date date = cal.getTime();

		return formatter.format(date);
	}

	public String generalisedTimetoDate(String generTime) {
		Date date = new Date();
		DateFormat formatter = new SimpleDateFormat(
				"MMM dd yyyy hh:mm:ss.SSS zzz");

		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

		try {
			date = new SimpleDateFormat("yyyyMMddHHmmss").parse(generTime
					.substring(0, 14));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formatter.format(date).toString();
	}
}
