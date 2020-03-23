package com.xwl.mybase.Application;

import android.content.Context;

import com.xwl.mybaselib.Base.BaseApplication;

public class Application extends android.app.Application {

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
