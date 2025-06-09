package com.custom.dlp.cmmn.utils;

public class StringUtils {

	/**
	 * <pre>
	 * 메소드명: isNumber
	 * 설명: 숫자체크
	 * </pre>
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		return str != null && str.matches("\\d+");
	}
}
