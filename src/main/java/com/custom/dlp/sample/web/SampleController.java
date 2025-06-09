package com.custom.dlp.sample.web;

import java.util.Enumeration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.custom.dlp.cmmn.model.CustomMap;
import com.custom.dlp.cmmn.utils.MapUtils;
import com.custom.dlp.cmmn.web.CustomController;
import com.custom.dlp.sample.model.A;
import com.custom.dlp.sample.model.B;
import com.custom.dlp.sample.model.SampleUser;
import com.custom.dlp.sample.service.SampleService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

/**
 * <pre>
 * 클래스명: SampleController
 * 설명: 샘플컨트롤러
 * </pre>
 */
@Controller @Log4j2
public class SampleController extends CustomController {
	
	@Autowired
	private SampleService sampleService;

	/**
	 * <pre>
	 * 메소드명: index
	 * 설명: index 페이지
	 * </pre>
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping("/")
	public ModelAndView index(ModelAndView modelAndView) {
		
		modelAndView.setViewName("index");
		
		return modelAndView;
	}
	
	/**
	 * <pre>
	 * 메소드명: dlpsp01
	 * 설명: sample01 페이지
	 * </pre>
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping("/DLPSP01")
	public ModelAndView dlpsp01(@RequestParam Map<String, Object> map, ModelAndView modelAndView) {
		
		CustomMap params = new CustomMap(map);
		
		MapUtils.paramsValidation(params, new String[][] {
			{ "requiredSno", "required" }
			, { "requiredSno", "number" }
		});
		
		if (log.isDebugEnabled()) { log.debug("params ::: " + params); }
		
		modelAndView.setViewName("sample/sample01");
		
		return modelAndView;
	}
	
	/**
	 * <pre>
	 * 메소드명: dlpsp02
	 * 설명: 샘플요청 DTO
	 * </pre>
	 * @param sampleUser
	 * @return
	 */
	@PostMapping("/DLPSP02")
	@ResponseBody
	public Object dlpsp02(@RequestBody SampleUser sampleUser) {
		CustomMap resultMap = new CustomMap();
		if (log.isDebugEnabled()) { log.debug("sampleUser ::: " + sampleUser); }
		
		resultMap.put("sampleUser", sampleUser);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: dlpsp03
	 * 설명: 샘플요청 CustomMap
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/DLPSP03")
	@ResponseBody
	public Object dlpsp03(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		MapUtils.paramsValidation(params, new String[][] {
			{ "sampleUserAge", "number" }
		});
		
		if (log.isDebugEnabled()) { log.debug("params ::: " + params); }
		
		resultMap.put("params", params);
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: dlpsp04
	 * 설명: 샘플유저정보 등록요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/DLPSP04")
	@ResponseBody
	public Object dlpsp04(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		if (log.isDebugEnabled()) { log.debug("params ::: " + params); }
		
		params.put("sysCreator", "SYSTEM");
		
		sampleService.insertSampleUserInfo(params);
		
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: dlpsp05
	 * 설명: 샘플유저기본정보 조회요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/DLPSP05")
	@ResponseBody
	public Object dlpsp05(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		if (log.isDebugEnabled()) { log.debug("params ::: " + params); }
		
		resultMap = sampleService.getSampleUser(params);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: dlpsp06
	 * 설명: 샘플유저상세정보 조회요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/DLPSP06")
	@ResponseBody
	public Object dlpsp06(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		if (log.isDebugEnabled()) { log.debug("params ::: " + params); }
		
		resultMap = sampleService.getSampleUserDtl(params);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: dlpsp07
	 * 설명: 샘플유저정보목록 조회요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/DLPSP07")
	@ResponseBody
	public Object dlpsp07(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		if (log.isDebugEnabled()) { log.debug("params ::: " + params); }
		
		resultMap = sampleService.getSampleUserInfoList(params);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: dlpsp08
	 * 설명: 샘플유저상세정보 등록요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/DLPSP08")
	@ResponseBody
	public Object dlpsp08(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		if (log.isDebugEnabled()) { log.debug("params ::: " + params); }
		
		params.put("sysCreator", "SYSTEM");
		
		resultMap = sampleService.insertSampleUserDtl(params);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: dlpsp09
	 * 설명: 샘플유저상세정보 수정요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/DLPSP09")
	@ResponseBody
	public Object dlpsp09(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		if (log.isDebugEnabled()) { log.debug("params ::: " + params); }
		
		params.put("sysModifier", "SYSTEM");
		
		resultMap = sampleService.updateSampleUserDtl(params);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: dlpsp10
	 * 설명: 샘플유저상세정보 삭제요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/DLPSP10")
	@ResponseBody
	public Object dlpsp10(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		if (log.isDebugEnabled()) { log.debug("params ::: " + params); }
		
		resultMap = sampleService.deleteSampleUserDtl(params);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: dlpsp11
	 * 설명: 샘플유저상세정보 수정요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/DLPSP11")
	@ResponseBody
	public Object dlpsp11(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		if (log.isDebugEnabled()) { log.debug("params ::: " + params); }
		
		params.put("sysActor", "SYSTEM");
		
		resultMap = sampleService.mergeSampleUserDtl(params);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: dlpsp12
	 * 설명: 샘플유저정보 등록요청 (수동롤백테스트)
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/DLPSP12")
	@ResponseBody
	public Object dlpsp12(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		if (log.isDebugEnabled()) { log.debug("params ::: " + params); }
		
		params.put("sysCreator", "SYSTEM");
		
		resultMap = sampleService.insertSampleUserInfo2(params);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: dlpsp13
	 * 설명: 양방향 매핑시 직렬화(객체 -> JSON) 순환참조
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/DLPSP13")
	@ResponseBody
	public Object dlpsp13() {
		CustomMap resultMap = new CustomMap();
		
		A a = new A();
		a.setName("name1");
		
		B b = new B();
		b.setPrice(1000);
		
		a.putB(b); // 양방향 매핑
		
		// toString 에선 순환참조 방지했어도 직렬화 순환참조랑 별개입니다
		// 직렬화/역직렬화 필드값을 통해 이루어지기에 다른 방식으로 순환참조를 막아야합니다.
		if (log.isDebugEnabled()) { log.debug("a ::: " + a); }
		
		resultMap.put("a", a);
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: dlpsp14
	 * 설명: 양방향 매핑시 역직렬화(JSON -> 객체) 순환참조
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/DLPSP14")
	@ResponseBody
	public Object dlpsp14(@RequestBody A a) {
		CustomMap resultMap = new CustomMap();
		
		// a 에서 b를 참조할 수 있고
		// b 에서도 a를 참조할 수 있어야 역직렬화가 제대로 이루어졌다고 할 수 있습니다
		if (log.isDebugEnabled()) { log.debug("a ::: " + a); }
		if (log.isDebugEnabled()) { log.debug("b ::: " + a.getB()); }
		if (log.isDebugEnabled()) { log.debug("b.a ::: " + a.getB().getA()); }
		
		return getResponse(resultMap);
	}
	
}
