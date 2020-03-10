package com.xwl.mybaselib.Base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class BaseApplication {

	public static List<Activity> activityList = new LinkedList<>();
	public static Application application;

	public BaseApplication(Application application) {
		this.application = application;
	}

	public static void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public static void removeActivity(Activity activity) {
		activityList.remove(activity);
	}

	public static void exit() {
		for (Activity a : activityList) {
			a.finish();
		}
		ActivityManager activityMgr = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
		if (activityMgr != null) {
			activityMgr.restartPackage(application.getPackageName());
		}
		System.exit(0);
	}

	public static Context getContext() {
		return application.getApplicationContext();
	}

	public static int getAppVersionCode(Context context) {
		int versionCode = 0;
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionCode = pi.versionCode;
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionCode;
	}

	public static String getAppFirstActivity() {
		String activityName = "";
		if (activityList != null && activityList.size() > 0) {
			activityName = activityList.get(activityList.size() - 1).getLocalClassName();
		}
		return activityName;
	}

	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}
}
