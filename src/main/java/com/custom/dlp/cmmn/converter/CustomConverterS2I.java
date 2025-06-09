package com.custom.dlp.cmmn.converter;

import com.custom.dlp.cmmn.exception.CustomException;
import com.custom.dlp.cmmn.exception.CustomExceptionCode;

import jakarta.persistence.AttributeConverter;

/**
 * <pre>
 * 클래스명: CustomConverterS2I
 * 설명: String to Integer 컨버터
 * </pre>
 */
public class CustomConverterS2I implements AttributeConverter<String, Integer> {

	@Override
	public Integer convertToDatabaseColumn(String attribute) {
		if (attribute == null || attribute.trim().isEmpty()) {
			return null;
		}
		
		try {
			return Integer.valueOf(attribute.trim().replace(",", ""));
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR602, new String[] { attribute }, e);
		}
	}

	@Override
	public String convertToEntityAttribute(Integer dbData) {
		return dbData == null ? "" : String.valueOf(dbData);
	}

}
