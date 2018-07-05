package com.xuyao.prancelib.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/*
保存临时数据
 */
public class PreferenceUtils {
	public static String getPrefString(Context context, String key,
			final String defaultValue) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getString(key, defaultValue);
	}

	public static void setPrefString(Context context, final String key,
			final String value) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putString(key, value).commit();
	}

	public static boolean getPrefBoolean(Context context, final String key,
			final boolean defaultValue) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getBoolean(key, defaultValue);
	}

	public static boolean hasKey(Context context, final String key) {
		return PreferenceManager.getDefaultSharedPreferences(context).contains(
				key);
	}

	public static void setPrefBoolean(Context context, final String key,
			final boolean value) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putBoolean(key, value).commit();
	}

	public static void setPrefInt(Context context, final String key,
			final int value) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putInt(key, value).commit();
	}

	public static int getPrefInt(Context context, final String key,
			final int defaultValue) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getInt(key, defaultValue);
	}

	public static void setPrefFloat(Context context, final String key,
			final float value) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putFloat(key, value).commit();
	}

	public static float getPrefFloat(Context context, final String key,
			final float defaultValue) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getFloat(key, defaultValue);
	}

	public static void setSettingLong(Context context, final String key,
			final long value) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putLong(key, value).commit();
	}

	public static long getPrefLong(Context context, final String key,
			final long defaultValue) {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getLong(key, defaultValue);
	}

	public static void clearPreference(Context context,
			final SharedPreferences p) {
		final Editor editor = p.edit();
		editor.clear();
		editor.commit();
	}
/**
 * 删除指定key
 */
	public  static void deleteKey(String key,Context c){
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(c);
		Editor e=settings.edit();
		e.remove(key);
		e.commit();
	}
	/**
	 * 存储集合
	 */
	public static void putPreferenceList(String key, List<Object> objects,
			Context context) {
		Gson gson = new Gson();
		String value = gson.toJson(objects);
		final SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		preferences.edit().putString(key, value).commit();
	}

	/**
	 * 获取list的集合
	 * 
	 * @param context
	 * @param key
	 * @param defaut_value
	 * @return
	 */
	public static List<Object> getPreferenceList(Context context, String key,
			String defaut_value) {
		Gson gson = new Gson();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		String value = preferences.getString(key, defaut_value);
		if (!value.equals(defaut_value)) {
			return gson.fromJson("" + value, new TypeToken<List<Object>>() {
			}.getType());
		} else {
			return null;
		}
	}

	/**
	 * 存储对象
	 * 
	 * @param context
	 * @param key
	 * @param object
	 */
	public static void putPreferenceObject(Context context, String key,
			Object object) {
		if(object==null){
			deleteKey(key,context);
			return;
		}
		Gson gson = new Gson();
		String value = gson.toJson(object);
		final SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		preferences.edit().putString(key, value).commit();
	}
	/**
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static Object getPreferenceObject(Context context,String key,Class<?> formatClass){
		Gson gson = new Gson();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		String value = preferences.getString(key, "{}");
		if (!value.equals("{}")) {
			return gson.fromJson("" + value,formatClass);
		} else {
			return null;
		}
	}
}
