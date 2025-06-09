package com.custom.dlp.cmmn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.custom.dlp.cmmn.interceptor.CustomURLInterceptor;

@Configuration
public class CustomWebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CustomURLInterceptor());
	}

	
}
