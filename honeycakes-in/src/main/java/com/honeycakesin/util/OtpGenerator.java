package com.honeycakesin.util;

public class OtpGenerator {

	/**
	 * generate method generated a 4 digit random number.
	 * 
	 * @return String
	 */
	public static String generate() {
		int otp = (int) (Math.random() * 9000) + 1000;
		return String.valueOf(otp);
	}

}
