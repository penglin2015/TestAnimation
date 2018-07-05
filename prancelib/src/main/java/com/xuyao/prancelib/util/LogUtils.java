package com.xuyao.prancelib.util;

import android.util.Log;

/**
 * 打印工具
 */
public class LogUtils {
	public static boolean isDebug = true;
	private static final String TAG = "pranceLog";

	// 下面四个是默认tag的函数
	public static void i(String msg) {
		if (isDebug)
			Log.i(TAG, msg);
	}

	public static void d(String msg) {
		if (isDebug)
			Log.d(TAG, msg);
	}

	public static void e(String msg) {
		if (isDebug)
			Log.e(TAG, msg);
	}

	public static void v(String msg) {
		if (isDebug)
			Log.v(TAG, msg);
	}

	// 下面是传入类名打印log
	public static void i(Class<?> _class, String msg) {
		if (isDebug)
			Log.i(_class.getName(), msg);
	}

	public static void d(Class<?> _class, String msg) {
		if (isDebug)
			Log.d(_class.getName(), msg);
	}

	public static void e(Class<?> _class, String msg) {
		if (isDebug)
			Log.e(_class.getName(), msg);
	}

	public static void v(Class<?> _class, String msg) {
		if (isDebug)
			Log.v(_class.getName(), msg);
	}

	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (isDebug)
			Log.d(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (isDebug)
			Log.e(tag, msg);
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (isDebug)
			Log.e(tag, msg, tr);
	}

	public static void v(String tag, String msg) {
		if (isDebug)
			Log.v(tag, msg);
	}

	public static void w(String tag, String msg) {
		if (isDebug)
			Log.w(tag, msg);
	}

	public static void w(String tag, String msg, Throwable tr) {
		if (isDebug)
			Log.w(tag, msg, tr);

	}

	public static void w(String tag, Throwable tr) {
		if (isDebug)
			Log.w(tag, tr);

	}

	public static void wtf(String tag, String msg) {
		if (isDebug)
			Log.wtf(tag, msg);

	}

	public static void logSystemPrint(String msg){
		if (isDebug)
		System.out.println(msg);
	}
}
