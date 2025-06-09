package com.custom.dlp.shpn2025.product.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.custom.dlp.cmmn.model.CustomMap;
import com.custom.dlp.cmmn.web.CustomController;
import com.custom.dlp.shpn2025.product.service.ProductService;

@Controller
public class ProductController extends CustomController {

	@Autowired
	private ProductService productService;
	
	/**
	 * <pre>
	 * 메소드명: shppd01
	 * 설명: 상품정보 등록요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/SHPPD01")
	@ResponseBody
	public Object shppd01(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		resultMap = productService.insertProductInfo(params);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: shppd02
	 * 설명: 상품정보 조회요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/SHPPD02")
	@ResponseBody
	public Object shppd02(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		resultMap = productService.getProductInfo(params);
		
		return getResponse(resultMap);
	}
	
	/**
	 * <pre>
	 * 메소드명: shppd03
	 * 설명: 상품정보 수정요청
	 * </pre>
	 * @param params
	 * @return
	 */
	@PostMapping("/SHPPD03")
	@ResponseBody
	public Object shppd03(@RequestBody CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		resultMap = productService.updateProductInfo(params);
		
		return getResponse(resultMap);
	}
}
