package com.example.demo.common.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {

	/** The Constant ERROR_KEY. */
	public static final String ERROR_KEY = "errors";

	/**
	 * Perform error.
	 *
	 * @param result the result
	 * @param key    the key
	 * @param value  the value
	 * @return the JSON object
	 */
	public static JSONObject performError(JSONObject result, String key, String value) {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(key, value);

			if (result == null) {
				result = new JSONObject();
			}

			JSONArray jsonArr;
			if (!result.has(ERROR_KEY)) {
				jsonArr = new JSONArray();
			} else {
				jsonArr = result.getJSONArray(ERROR_KEY);
			}

			jsonArr.put(jsonObject);
			result.put(ERROR_KEY, jsonArr);
		} catch (JSONException e) {
			// TODO: handle exception
		}
		return result;
	}

	/**
	 * Perform error.
	 *
	 * @param message the message
	 * @return the JSON object
	 */
	public static JSONObject performError(String message) {
		JSONObject result = null;
		try {
			result = new JSONObject();
			result.put(ERROR_KEY, message);
		} catch (JSONException e) {
			// TODO: handle exception
		}
		return result;
	}
}
