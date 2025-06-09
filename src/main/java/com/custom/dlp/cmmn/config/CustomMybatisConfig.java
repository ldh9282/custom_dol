package com.custom.dlp.cmmn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.custom.dlp.cmmn.interceptor.CustomSqlLoggingInterceptor;

@Configuration
public class CustomMybatisConfig {

	@Bean
	public CustomSqlLoggingInterceptor customSqlLoggingInterceptor() {
		return new CustomSqlLoggingInterceptor();
	}
}
