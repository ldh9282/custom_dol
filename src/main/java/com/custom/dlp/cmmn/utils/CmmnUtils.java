package com.custom.dlp.cmmn.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * <pre>
 * 클래스명: CmmnUtils
 * 설명: 공통유틸
 * </pre>
 */
@Component
public class CmmnUtils {
	
	private static HashMap<String, String> initMap;
	
	@Value("${server.servlet.context-path}")
	private String contextPath;
	
	@Value("${server.port}")
	private String serverPort;
	
	@Value("${server-ip-logging-yn}")
	private String serverIpLoggingYn;
	
	@PostConstruct
	private void init() {
		initMap = new HashMap<String, String>();
		initMap.put("contextPath", contextPath);
		initMap.put("serverPort", serverPort);
		initMap.put("serverIpLoggingYn", serverIpLoggingYn);
	}
	
	/**
	 * <pre>
	 * 메소드명: getServerIP
	 * 설명: 서버 아이피 반환
	 * </pre>
	 * @return
	 */
	public static String getServerIP() {
		String serverIP = "";
		
		InetAddress localhost;
		
		try {
			localhost = InetAddress.getLocalHost();
			serverIP = localhost.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if ("0".equals(initMap.getOrDefault("serverIpLoggingYn", ""))) {
			return "xxx.xxx.xx.xx";
		}
		
		return serverIP;
	}
	
	public static String getContextPath() {
		return initMap.getOrDefault("contextPath", "");
	}
	
	public static String getServerPort() {
		return initMap.getOrDefault("serverPort", "");
	}

}
