package com.custom.dlp.cmmn.web;

import com.custom.dlp.cmmn.exception.CustomException;
import com.custom.dlp.cmmn.model.CustomMap;

/**
 * <pre>
 * 클래스명: CustomController
 * 설명: 공통처리를 위해 상속한다
 * </pre>
 */
public class CustomController {


	/**
	 * <pre>
	 * 메소드명: getResponse
	 * 설명: ajax 성공응답
	 * --------------------------------
	 * 반환 프로퍼티
	 * --------------------------------
	 * response.header.status == 0000
	 * response.body
	 * </pre>
	 * @param object
	 * @return
	 */
	public static CustomMap getResponse(Object object) {
		CustomMap response = new CustomMap();
		
		CustomMap header = new CustomMap();
		
		header.put("status", "0000");
		response.put("header", header);
		response.put("body", object);
		
		return response;
	}
	
	/**
	 * <pre>
	 * 메소드명: getErrorResponse
	 * 설명: ajax 실패응답
	 * --------------------------------
	 * 반환 프로퍼티
	 * --------------------------------
	 * response.header.status != 0000
	 * response.header.errorMsg
	 * </pre>
	 * @param e
	 * @return
	 */
	public static CustomMap getErrorResponse(CustomException e) {
		CustomMap response = new CustomMap();
		
		CustomMap header = new CustomMap();
		
		header.put("status", e.getCustomExceptionCode().getErrorCode());
		header.put("errorMsg", e.getMessage());
		response.put("header", header);
		
		return response;
	}
}
