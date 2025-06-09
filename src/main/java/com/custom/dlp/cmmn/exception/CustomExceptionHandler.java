package com.custom.dlp.cmmn.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.custom.dlp.cmmn.web.CustomController;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class CustomExceptionHandler extends CustomController {

	@ExceptionHandler(CustomException.class)
	public Object handleCustomException(HttpServletRequest request, CustomException e) {
		boolean isAjaxRequest = "application/json".equals(request.getHeader("Content-Type"));
		
		if (isAjaxRequest) {
			return getErrorResponse(e);
		} else {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("response", getErrorResponse(e));
			modelAndView.setViewName("cmmn/error-500");
			return modelAndView;
		}
	}
}
