package com.educa.utils;

import android.app.Application;
import android.content.Context;

public class ActiveSession extends Application {

	private static Context sContext;

	@Override
	public final void onCreate() {
		super.onCreate();
		setContext(this);
	}

	public static Context getAppContext() {
		return sContext;
	}

	public static void setContext(final Context context) {
		sContext = context;
	}

}
