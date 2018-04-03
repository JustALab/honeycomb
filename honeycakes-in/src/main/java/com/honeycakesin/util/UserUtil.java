package com.honeycakesin.util;

public class UserUtil {

	private static String USERNAME;

	public static void setUserName(String usernameFromToken) {
		USERNAME = usernameFromToken;
	}

	public static String getUserName() {
		return USERNAME;
	}

}
