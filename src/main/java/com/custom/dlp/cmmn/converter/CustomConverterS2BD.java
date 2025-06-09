package com.custom.dlp.cmmn.converter;

import java.math.BigDecimal;

import com.custom.dlp.cmmn.exception.CustomException;
import com.custom.dlp.cmmn.exception.CustomExceptionCode;

import jakarta.persistence.AttributeConverter;

/**
 * <pre>
 * 클래스명: CustomConverterS2BD
 * 설명: String to BigDecimal 컨버터
 * </pre>
 */
public class CustomConverterS2BD implements AttributeConverter<String, BigDecimal> {

	@Override
	public BigDecimal convertToDatabaseColumn(String attribute) {
		if (attribute == null || attribute.trim().isEmpty()) {
			return null;
		}
		
		try {
			return new BigDecimal(attribute.trim().replace(",", ""));
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR602, new String[] { attribute }, e);
		}
	}

	@Override
	public String convertToEntityAttribute(BigDecimal dbData) {
		return dbData == null ? "" : dbData.stripTrailingZeros().toPlainString();
	}

}
