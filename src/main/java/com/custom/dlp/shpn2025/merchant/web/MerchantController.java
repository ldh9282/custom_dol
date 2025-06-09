package com.custom.dlp.shpn2025.merchant.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.custom.dlp.cmmn.model.CustomMap;
import com.custom.dlp.cmmn.paging.PagingCreator;
import com.custom.dlp.cmmn.web.CustomController;
import com.custom.dlp.shpn2025.merchant.service.MerchantService;

@Controller
public class MerchantController extends CustomController {

	@Autowired
	private MerchantService merchantService;
	
	/**
	 * <pre>
	 * 메소드명: shpmc01
	 * 설명: 입점사기본정보 등록요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/SHPMC01")
	@ResponseBody
	public Object shpmc01(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		resultMap = merchantService.insertMerchantInfo(params);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: shpmc02
	 * 설명: 입점사기본정보 조회요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/SHPMC02")
	@ResponseBody
	public Object shpmc02(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		resultMap = merchantService.getMerchantInfo(params);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: shpmc03
	 * 설명: 입점사 상품목록 조회요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/SHPMC03")
	@ResponseBody
	public Object shpmc03(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		resultMap = merchantService.getProductInfoList(params);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: shpmc04
	 * 설명: 입점사 상품목록 조회요청2
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/SHPMC04")
	@ResponseBody
	public Object shpmc04(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		CustomMap productInfoListMap = merchantService.getProductInfoList2(params);
		
		List<CustomMap> productInfoList = productInfoListMap.getCustomMapList("productInfoList");
		params.put("count", productInfoListMap.getString("count"));
		
		CustomMap pagingCreator = new PagingCreator(params).toCustomMap();
		
		resultMap.put("productInfoList", productInfoList);
		resultMap.put("pagingCreator", pagingCreator);
		
		return getResponse(resultMap);
	}
}
