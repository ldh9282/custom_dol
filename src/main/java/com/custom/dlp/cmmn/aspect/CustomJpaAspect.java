package com.custom.dlp.cmmn.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

/**
 * <pre>
 * 클래스명: CustomJpaAspect
 * 설명: JPA 로깅을 위한 Aspect
 * </pre>
 */
@Aspect
@Component @Log4j2
public class CustomJpaAspect {

	@Before("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))")
	public void logBefore(JoinPoint joinPoint) {
	    String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
	    String methodName = joinPoint.getSignature().getName();

	    if (log.isDebugEnabled()) {
	        log.debug("JPA 호출 위치: {}.{}", className, methodName);
	        Object[] args = joinPoint.getArgs();
	        for (Object arg : args) {
	            log.debug("바인딩 파라미터: {}", arg);
	        }
	    }
	}
}
