package com.custom.dlp.sample2025.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.custom.dlp.cmmn.model.CustomMap;
import com.custom.dlp.cmmn.web.CustomController;
import com.custom.dlp.sample2025.service.SampleUserService;

@Controller
public class SampleUserController extends CustomController {

	
	@Autowired
	private SampleUserService sampleUserService;
	
	/**
	 * <pre>
	 * 메소드명: samus01
	 * 설명: 샘플유저정보 등록요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/SAMUS01")
	@ResponseBody
	public Object samus01(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		resultMap = sampleUserService.insertSampleUserInfo(params);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: samus02
	 * 설명: 샘플유저정보 조회요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/SAMUS02")
	@ResponseBody
	public Object samus02(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		resultMap = sampleUserService.getSampleUserInfo(params);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: samus03
	 * 설명: 샘플유저정보 수정요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/SAMUS03")
	@ResponseBody
	public Object samus03(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		resultMap = sampleUserService.updateSampleUserInfo(params);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: samus04
	 * 설명: 샘플유저정보 삭제요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/SAMUS04")
	@ResponseBody
	public Object samus04(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		resultMap = sampleUserService.deleteSampleUserInfo(params);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: samus05
	 * 설명: 샘플유저정보 수정요청2
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/SAMUS05")
	@ResponseBody
	public Object samus05(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		resultMap = sampleUserService.updateSampleUserInfo2(params);
		
		return getResponse(resultMap);
	}
}
