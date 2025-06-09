package com.custom.dlp.cmmn.interceptor;

import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 * <pre>
 * 클래스명: CustomURLInterceptor
 * 설명: URL 을 로깅 식별자로 사용하기 위한 인터셉터
 * </pre>
 */
@Log4j2
public class CustomURLInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		try {
			if (handler instanceof HandlerMethod) {
				
				long startTime = System.currentTimeMillis();
				request.setAttribute("startTime", startTime);
				
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				String className = handlerMethod.getBeanType().getName();
				
				// 패키지 이름을 제외한 클래스 이름 추출
				int lastIndex = className.lastIndexOf(".");
				if (lastIndex != -1) {
					className = className.substring(lastIndex + 1);
				}
				
				String methodName = handlerMethod.getMethod().getName();
				
				// /DLSP01 => DLSP01
				String theRequestUrl = request.getRequestURI().substring(1);
				String queryString = request.getQueryString() == null ? "" : "?" + request.getQueryString();
				String requestUrl = theRequestUrl + queryString;
				
				request.setAttribute("requestUrl", requestUrl);
				
				// URL 식별자(쿼리스트링제외)는 11 글자까지만 허용
				String identifier = theRequestUrl.length() < 12 ? theRequestUrl : theRequestUrl.substring(0, 11);
				MDC.put("identifier", identifier);
				
				if (log.isDebugEnabled()) { log.debug(">>> Start Controller ::: " + className + "." + methodName); }
				
			}
		} catch (Exception e) {
			String requestUrl = (String) request.getAttribute("requestUrl");
			if (log.isErrorEnabled()) { log.error(">>> Exception ::: " + e.getMessage()); }
			if (log.isErrorEnabled()) { log.error(">>> error request ::: url ::: " + requestUrl); }
			return false;
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if (modelAndView != null) {
			request.setAttribute("viewName", modelAndView.getViewName());
		} else {
			request.setAttribute("viewName", null);
			
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		try {
			if (handler instanceof HandlerMethod) {
				long startTime = (long) request.getAttribute("startTime");
				long endTime = System.currentTimeMillis();
				
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				String className = handlerMethod.getBeanType().getName();
				
				// 패키지 이름을 제외한 클래스 이름 추출
				int lastIndex = className.lastIndexOf(".");
				if (lastIndex != -1) {
					className = className.substring(lastIndex + 1);
				}
				
				String methodName = handlerMethod.getMethod().getName();
				String viewName = (String) request.getAttribute("viewName");
				
				if (viewName != null) {
					if (log.isDebugEnabled()) { log.debug("<<< viewName ::: " + viewName); }
				}
				if (log.isDebugEnabled()) { log.debug("<<< execution time ::: " + (endTime - startTime) + "ms"); }
				if (log.isDebugEnabled()) { log.debug("<<< End Controller ::: " + className + "." + methodName); }
				
			}
		} catch (Exception e) {
			String requestUrl = (String) request.getAttribute("requestUrl");
			if (log.isErrorEnabled()) { log.error("<<< Exception ::: " + e.getMessage()); }
			if (log.isErrorEnabled()) { log.error("<<< error request ::: url ::: " + requestUrl); }
		}
		
		MDC.clear();
	}
	
	
	

}
