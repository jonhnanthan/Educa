package com.educaTio.utils;

public class ActiveSession {
	
	private static String activeLogin;
	
	public static String getActiveLogin() {
		return activeLogin;
	}

	public static void setActiveLogin(String activeLogin) {
		ActiveSession.activeLogin = activeLogin;
	}
}
