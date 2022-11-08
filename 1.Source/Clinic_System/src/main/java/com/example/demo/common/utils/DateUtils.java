package com.example.demo.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils {

	/** The Constant LOGGER. */
	public static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

	/**
	 * String to date.
	 *
	 * @param strDate the str date
	 * @param pattern the pattern
	 * @return the date
	 */
	public static Date stringToDate(String strDate, String pattern) {
		if (strDate == null || strDate.isEmpty()) {
			return null;
		}
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			LOGGER.error("DateUtils stringToDate():" + e);
		}
		return date;
	}

	/**
	 * Date to string.
	 *
	 * @param date    the date
	 * @param pattern the pattern
	 * @return the string
	 */
	public static String dateToString(Date date, String pattern) {
		String result = null;
		if (date == null) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			result = sdf.format(date);
		} catch (Exception e) {
			LOGGER.error("DateUtils dateToString():" + e);
		}
		return result;
	}

}
