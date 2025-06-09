package com.custom.dlp.cmmn.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.custom.dlp.cmmn.utils.HttpUtils;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping("/DLPER01")
	public ModelAndView dlper01(HttpServletRequest request, ModelAndView modelAndView) {
		
		HttpStatus status = HttpUtils.getStatus(request);
		
		if (status == HttpStatus.NOT_FOUND) {
			modelAndView.setViewName("cmmn/error-404");
		} else if (status == HttpStatus.FORBIDDEN) {
			modelAndView.setViewName("cmmn/error-403");
		} else if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
			modelAndView.setViewName("cmmn/error-500");
		} else {
			modelAndView.setViewName("cmmn/error-500");
		}
		
		return modelAndView;
	}
}
