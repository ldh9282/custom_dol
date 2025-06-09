package com.custom.dlp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;

import com.custom.dlp.cmmn.utils.CmmnUtils;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication @Log4j2
public class DLPApplication {

	
	
	public static void main(String[] args) {
		SpringApplication.run(DLPApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(String[] args) {
		return runner -> {
			if (log.isDebugEnabled()) { log.debug("localhost:" + CmmnUtils.getServerPort() + CmmnUtils.getContextPath()); }
			if (log.isDebugEnabled()) { log.debug(CmmnUtils.getServerIP() + ":" + CmmnUtils.getServerPort() + CmmnUtils.getContextPath()); }
			if (log.isDebugEnabled()) { log.debug("서버구동이 시작되었습니다"); }
		};
	}
	
	@Bean
	public ApplicationListener<ContextClosedEvent> applicationListener() {
		return new ApplicationListener<ContextClosedEvent>() {
			
			@Override
			public void onApplicationEvent(ContextClosedEvent event) {
				long startTime = event.getApplicationContext().getStartupDate();
				long endTime = System.currentTimeMillis();
				
				Instant startTimeInstant = Instant.ofEpochMilli(startTime);
				Instant endTimeInstant = Instant.ofEpochMilli(endTime);
				
				
				LocalDateTime startTimeLocalDateTime = LocalDateTime.ofInstant(startTimeInstant, ZoneId.systemDefault());
				LocalDateTime endTimeLocalDateTime = LocalDateTime.ofInstant(endTimeInstant, ZoneId.systemDefault());
				
				if (log.isDebugEnabled()) { log.debug("서버 구동시작 ::: " + startTimeLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd a hh:mm:ss"))); }
				if (log.isDebugEnabled()) { log.debug("서버 구동종료 ::: " + endTimeLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd a hh:mm:ss"))); }
				if (log.isDebugEnabled()) { log.debug("서버 총 구동시간 ::: " + (endTime - startTime) / 1000 / 3600 + " 시간 " + (endTime - startTime) / 1000 % 3600 / 60 + " 분 " + (endTime - startTime) / 1000 % 60 + " 초"); }
				if (log.isDebugEnabled()) { log.debug("서버구동이 종료되었습니다"); }
				
				
			}
		};
	}
}
