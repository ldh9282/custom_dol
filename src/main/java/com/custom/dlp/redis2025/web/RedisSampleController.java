package com.custom.dlp.redis2025.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.custom.dlp.cmmn.cache.RedisUtils;
import com.custom.dlp.cmmn.model.CustomMap;
import com.custom.dlp.cmmn.web.CustomController;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Controller @Log4j2
public class RedisSampleController extends CustomController {

	@PostMapping("/DLPRD00")
	@ResponseBody
	public Object dlprd00(@RequestBody CustomMap params, HttpSession httpSession) {
		CustomMap resultMap = new CustomMap();
		
		httpSession.setAttribute("username", params.getString("username"));
		
		return getResponse(resultMap);
	}
	
	@PostMapping("/DLPRD01")
	@ResponseBody
	public Object dlprd01(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		CustomMap cache = RedisUtils.getCmmnCache();
		if (cache.getCustomMap("a") == null) {
			CustomMap a = new CustomMap();
			a.put("data", "a1234");
			RedisUtils.putCmmnCache("a", a);
			resultMap.put("a", a);
			if (log.isDebugEnabled()) log.debug("no cache");
		} else {
			resultMap.put("a", cache.getCustomMap("a"));
			if (log.isDebugEnabled()) log.debug("cache");
		}
		
		return getResponse(resultMap);
	}
	
	@PostMapping("/DLPRD02")
	@ResponseBody
	public Object dlprd02(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		CustomMap userCache = RedisUtils.getUserCache();
		if (userCache.getCustomMap("a") == null) {
			CustomMap a = new CustomMap();
			a.put("data", "a1234");
			RedisUtils.putUserCache("a", a);
			resultMap.put("a", a);
			if (log.isDebugEnabled()) log.debug("no cache");
		} else {
			resultMap.put("a", userCache.getCustomMap("a"));
			if (log.isDebugEnabled()) log.debug("cache");
		}
		
		return getResponse(resultMap);
	}
}
