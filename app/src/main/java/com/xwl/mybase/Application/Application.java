package com.xwl.mybase.Application;

import android.app.Activity;
import android.content.Context;

import com.xwl.mybaselib.Base.BaseApplication;

import java.util.LinkedList;
import java.util.List;

public class Application extends android.app.Application {

	public static List<Activity> activityList = new LinkedList<>();
	public static Application application;
	public static BaseApplication baseApplication;

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		baseApplication = new BaseApplication(application);
	}

	public Application() {
	}
}
