package com.custom.dlp.redis2025.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.custom.dlp.cmmn.model.CustomMap;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;

@Service
public class RedisSampleService {

	private static final String CACHE_CMMN_MAP_KEY = "CACHE_CMMN_MAP_KEY";
	private static final String CACHE_USER_MAP_PREFIX = "USER:";
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private HttpSession session;
	
	@PostConstruct
	private void init() {
		if (redisTemplate.opsForValue().get(CACHE_CMMN_MAP_KEY) == null) {
			redisTemplate.opsForValue().set(CACHE_CMMN_MAP_KEY, new CustomMap());
		}
	}
	
	public void putCmmnCache(String key, Object value) {
		CustomMap cache = (CustomMap) redisTemplate.opsForValue().get(CACHE_CMMN_MAP_KEY);
		if (cache == null) {
			cache = new CustomMap();
		}
		cache.put(key, value);
		redisTemplate.opsForValue().set(CACHE_CMMN_MAP_KEY, cache);
	}
	
	public CustomMap getCmmnCache() {
		CustomMap cache = (CustomMap) redisTemplate.opsForValue().get(CACHE_CMMN_MAP_KEY);
		return cache != null ? cache : new CustomMap();
	}
	
	public void putUserCache(String key, Object value) {
		String username = (String) session.getAttribute("username");
		CustomMap cache = (CustomMap) redisTemplate.opsForValue().get(CACHE_USER_MAP_PREFIX + username);
		if (cache == null) {
			cache = new CustomMap();
		}
		cache.put(key, value);
		redisTemplate.opsForValue().set(CACHE_USER_MAP_PREFIX + username, cache);
	}
	
	public CustomMap getUserCache() {
		String username = (String) session.getAttribute("username");
		CustomMap cache = (CustomMap) redisTemplate.opsForValue().get(CACHE_USER_MAP_PREFIX + username);
		return cache != null ? cache : new CustomMap();
	}
	
}
