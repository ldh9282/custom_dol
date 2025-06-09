package com.custom.dlp.cmmn.converter;

import com.custom.dlp.cmmn.exception.CustomException;
import com.custom.dlp.cmmn.exception.CustomExceptionCode;

import jakarta.persistence.AttributeConverter;

/**
 * <pre>
 * 클래스명: CustomConverterS2L
 * 설명: String to Long 컨버터
 * </pre>
 */
public class CustomConverterS2L implements AttributeConverter<String, Long> {

	@Override
	public Long convertToDatabaseColumn(String attribute) {
		if (attribute == null || attribute.trim().isEmpty()) {
			return null;
		}
		
		try {
			return Long.valueOf(attribute.trim().replace(",", ""));
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR602, new String[] { attribute }, e);
		}
	}

	@Override
	public String convertToEntityAttribute(Long dbData) {
		return dbData == null ? "" : String.valueOf(dbData);
	}

}
