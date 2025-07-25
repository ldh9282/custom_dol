package com.custom.dlp.cmmn.exception;

/**
 * <pre>
 * 클래스명: CustomExceptionCode
 * 설명: 예외코드
 * </pre>
 */
public enum CustomExceptionCode {

	/**
	 * 시스템 내부 처리중 오류가 발생했습니다.
	 */
	ERR500("ERR500", "시스템 내부 처리중 오류가 발생했습니다."),
	/**
	 * [{0}] 시스템 내부 처리중 오류가 발생했습니다.
	 */
	ERR501("ERR501", "[{0}] 시스템 내부 처리중 오류가 발생했습니다."),
	/**
	 * [{0}] 데이터 조회 중 오류가 발생했습니다.
	 */
	ERR511("ERR511", "[{0}] 데이터 조회 중 오류가 발생했습니다."),
	/**
	 * [{0}] 데이터 등록 중 오류가 발생했습니다.
	 */
	ERR521("ERR521", "[{0}] 데이터 등록 중 오류가 발생했습니다."),
	/**
	 * [{0}] 데이터 수정 중 오류가 발생했습니다.
	 */
	ERR531("ERR531", "[{0}] 데이터 수정 중 오류가 발생했습니다."),
	/**
	 * [{0}] 데이터 삭제 중 오류가 발생했습니다.
	 */
	ERR541("ERR541", "[{0}] 데이터 삭제 중 오류가 발생했습니다."),
	/**
	 * [{0}] 은 필수값입니다.
	 */
	ERR600("ERR600", "[{0}] 은 필수값입니다."),
	/**
	 * [{0} : {1}] 은 숫자가 아닙니다.
	 */
	ERR601("ERR601", "[{0} : {1}] 은 숫자가 아닙니다."),
	/**
	 * [{0}] 은 숫자가 아닙니다.
	 */
	ERR602("ERR602", "[{0}] 은 숫자가 아닙니다."),
	/**
	 * {0}
	 */
	ERR999("ERR999", "{0}"),
	;
	
	private final String errorCode;
	private final String errorMessage;
	
	private CustomExceptionCode(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	
}
