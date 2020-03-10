package com.xwl.mybaselib.Base.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class SharePreferenceDataUtil {
	private static SharedPreferences sp;
	private static SharedPreferences.Editor editor;

	@SuppressLint("CommitPrefEdits")
	private static void init(Context context) {
		if (sp == null) {
			// 实际可修改这里为实际的sp xml文件
			if (TextUtils.isEmpty("pd_ac")) {
				sp = PreferenceManager.getDefaultSharedPreferences(context);
				editor = sp.edit();
			} else {
				sp = context.getSharedPreferences("pd_ac", 0);
				editor = sp.edit();
			}
		}
	}

	public static void setSharedIntData(Context context, String key, int value) {
		if (sp == null) {
			init(context);
		}
		editor.putInt(key, value).commit();
	}

	public static int getSharedIntData(Context context, String key) {
		if (sp == null) {
			init(context);
		}
		return sp.getInt(key, 0);
	}

	public static int getSharedIntData(Context context, String key, int val) {
		if (sp == null) {
			init(context);
		}
		return sp.getInt(key, val);
	}

	public static void setSharedlongData(Context context, String key, long value) {
		if (sp == null) {
			init(context);
		}
		editor.putLong(key, value).commit();
	}

	public static long getSharedlongData(Context context, String key) {
		if (sp == null) {
			init(context);
		}
		return sp.getLong(key, 0l);
	}

	public static long getSharedlongData(Context context, String key, long time) {
		if (sp == null) {
			init(context);
		}
		return sp.getLong(key, time);
	}

	public static void setSharedFloatData(Context context, String key,
										  float value) {
		if (sp == null) {
			init(context);
		}
		editor.putFloat(key, value).commit();
	}

	public static Float getSharedFloatData(Context context, String key) {
		if (sp == null) {
			init(context);
		}
		return sp.getFloat(key, 0f);
	}

	public static Float getSharedFloatData(Context context, String key, float val) {
		if (sp == null) {
			init(context);
		}
		return sp.getFloat(key, val);
	}

	public static void setSharedBooleanData(Context context, String key,
											boolean value) {
		if (sp == null) {
			init(context);
		}
		editor.putBoolean(key, value).commit();
	}

	public static Boolean getSharedBooleanData(Context context, String key) {
		if (sp == null) {
			init(context);
		}
		return sp.getBoolean(key, false);
	}

	public static Boolean getSharedBooleanData(Context context, String key, boolean val) {
		if (sp == null) {
			init(context);
		}
		return sp.getBoolean(key, val);
	}

	public static void setSharedStringData(Context context, String key,
										   String value) {
		if (sp == null) {
			init(context);
		}
		editor.putString(key, value).commit();
	}

	public static String getSharedStringData(Context context, String key, String val) {
		if (sp == null) {
			init(context);
		}
		return sp.getString(key, val);
	}

	public static String getSharedStringData(Context context, String key) {
		if (sp == null) {
			init(context);
		}
		return sp.getString(key, "");
	}
}

