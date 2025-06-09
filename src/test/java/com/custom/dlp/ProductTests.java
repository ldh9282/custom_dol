package com.custom.dlp;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.custom.dlp.cmmn.model.CustomMap;
import com.custom.dlp.shpn2025.product.service.ProductService;

@SpringBootTest
public class ProductTests {

	@Autowired
	private ProductService productService;
	
	@Test
	void test01() {
		
		for (int i = 0; i < 100; i++) {
			CustomMap params = new CustomMap();
			params.put("merchantSno", "1");
			params.put("productName", "테스트상품명" + i);
			params.put("productPrice", "1000");
			
			List<CustomMap> productOptionList = new ArrayList<>();
			CustomMap productOption1 = new CustomMap();
			productOption1.put("productOptionName", "옵션명1");
			productOption1.put("productOptionAddPrice", "0");
			
			CustomMap productOption2 = new CustomMap();
			productOption2.put("productOptionName", "옵션명2");
			productOption2.put("productOptionAddPrice", "500");
			
			productOptionList.add(productOption1);
			productOptionList.add(productOption2);
			
			params.put("productOptionList", productOptionList);
			productService.insertProductInfo(params);
		}
	}
}
