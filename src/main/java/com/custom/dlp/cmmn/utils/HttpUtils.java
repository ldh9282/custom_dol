package com.custom.dlp.cmmn.utils;

import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpServletRequest;

public class HttpUtils {

	public static HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
		
		if (statusCode != null) {
			try {
				return HttpStatus.valueOf(statusCode);
			} catch (Exception e) {
				return HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
