package com.custom.dlp.cmmn.utils;

import com.custom.dlp.cmmn.exception.CustomException;
import com.custom.dlp.cmmn.exception.CustomExceptionCode;
import com.custom.dlp.cmmn.model.CustomMap;

public class MapUtils {

	/**
	 * <pre>
	 * 메소드명: paramsValidation
	 * 설명: 파라미터 유효성검사
	 * </pre>
	 * @param params
	 * @param validationList new String[][] { { "key", "format" } }
	 */
	public static void paramsValidation(CustomMap params, String[][] validationList) {
		for (String key : params.keySet()) {
			if (params.get(key) instanceof String) {
				params.put(key, params.getString(key).trim());
			}
		}
		
		if (validationList != null) {
			for (String[] validation : validationList) {
				String key = validation[0];
				
				String format = "";
				
				if (validation.length == 2) {
					format = validation[1];
				}
				
				if ("required".equals(format)) {
					if ("".equals(params.getString(key))) {
						throw new CustomException(CustomExceptionCode.ERR600, new String[] { key });
					}
				}
				
				if ("number".equals(format)) {
					if (!"".equals(params.getString(key))) {
						if (!StringUtils.isNumber(params.getString(key))) {
							throw new CustomException(CustomExceptionCode.ERR601, new String[] { key, params.getString(key) });
						}
					}
				}
			}
		}
	}
}
