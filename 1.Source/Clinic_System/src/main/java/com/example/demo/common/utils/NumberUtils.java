package com.example.demo.common.utils;

import org.apache.commons.lang3.StringUtils;


public class NumberUtils {

	public static Double parseDouble(final String str) {
		if (StringUtils.isNotBlank(str)) {
			try {
				return Double.parseDouble(str.trim());
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
