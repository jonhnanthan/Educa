package com.educaTio.utils;

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

	private static String activeLogin;
	
	public static String getActiveLogin() {
		return activeLogin;
	}

	public static void setActiveLogin(String activeLogin) {
		ActiveSession.activeLogin = activeLogin;
	}

	public static String getDefaultFolder() {
		return "teste";
	}
}
