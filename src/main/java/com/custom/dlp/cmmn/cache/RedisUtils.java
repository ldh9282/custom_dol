package com.custom.dlp.cmmn.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.custom.dlp.cmmn.model.CustomMap;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;

/**
 * <pre>
 * 클래스명: RedisUtils
 * 설명: 캐시를 위한 레디스 유틸
 * </pre>
 */
@Component
public class RedisUtils {
	
	private static RedisUtils instance;

	private static final String CACHE_CMMN_MAP_KEY = "CACHE_CMMN_MAP_KEY";
	private static final String CACHE_USER_MAP_PREFIX = "USER:";
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private HttpSession session;
	
	@PostConstruct
	private void init() {
		instance = this;
	}
	
	/**
	 * <pre>
	 * 메소드명: putCmmnCache
	 * 설명: 공통캐시 put
	 * </pre>
	 * @param key
	 * @param value
	 */
	public static void putCmmnCache(String key, Object value) {
		CustomMap cache = (CustomMap) instance.redisTemplate.opsForValue().get(CACHE_CMMN_MAP_KEY);
		if (cache == null) {
			cache = new CustomMap();
		}
		cache.put(key, value);
		instance.redisTemplate.opsForValue().set(CACHE_CMMN_MAP_KEY, cache);
	}
	
	/**
	 * <pre>
	 * 메소드명: getCmmnCache
	 * 설명: 공통캐시 get
	 * </pre>
	 * @return
	 */
	public static CustomMap getCmmnCache() {
		CustomMap cache = (CustomMap) instance.redisTemplate.opsForValue().get(CACHE_CMMN_MAP_KEY);
		return cache != null ? cache : new CustomMap();
	}
	
	/**
	 * <pre>
	 * 메소드명: putUserCache
	 * 설명: 유저캐시 put
	 * </pre>
	 * @param key
	 * @param value
	 */
	public static void putUserCache(String key, Object value) {
		String username = (String) instance.session.getAttribute("username");
		CustomMap cache = (CustomMap) instance.redisTemplate.opsForValue().get(CACHE_USER_MAP_PREFIX + username);
		if (cache == null) {
			cache = new CustomMap();
		}
		cache.put(key, value);
		instance.redisTemplate.opsForValue().set(CACHE_USER_MAP_PREFIX + username, cache);
	}
	
	/**
	 * <pre>
	 * 메소드명: getUserCache
	 * 설명: 유저캐시 get
	 * </pre>
	 * @param key
	 * @param value
	 */
	public static CustomMap getUserCache() {
		String username = (String) instance.session.getAttribute("username");
		CustomMap cache = (CustomMap) instance.redisTemplate.opsForValue().get(CACHE_USER_MAP_PREFIX + username);
		return cache != null ? cache : new CustomMap();
	}
	
}
