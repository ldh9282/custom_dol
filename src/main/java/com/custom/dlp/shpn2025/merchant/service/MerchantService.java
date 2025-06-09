package com.custom.dlp.shpn2025.merchant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.custom.dlp.cmmn.exception.CustomException;
import com.custom.dlp.cmmn.exception.CustomExceptionCode;
import com.custom.dlp.cmmn.model.CustomMap;
import com.custom.dlp.cmmn.utils.MapUtils;
import com.custom.dlp.shpn2025.merchant.entity.Merchant;
import com.custom.dlp.shpn2025.product.service.ProductDao;
import com.custom.dlp.shpn2025.product.service.ProductRepository;

@Service
public class MerchantService {

	@Autowired
	private MerchantRepository merchantRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductDao productDao;
	
	@Transactional
	public CustomMap insertMerchantInfo(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			Merchant merchant = Merchant.builder()
					.merchantName(params.getString("merchantName"))
					.merchantLoginId(params.getString("merchantLoginId"))
					.merchantLoginPw(params.getString("merchantLoginPw"))
					.build();
			
			Merchant theMerchant = merchantRepository.save(merchant);
			
			resultMap.put("merchant", theMerchant);
			
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR521, new String[] { "입점사정보" }, e);
		}
		
		return resultMap;
	}
	
	public CustomMap getMerchantInfo(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			// 파라미터 필수값 체크
			MapUtils.paramsValidation(params, new String[][] {
				{ "merchantSno", "required" }
				, { "merchantSno", "number" }
			});
			
			// 옵셔널 쓸때는 타입추론 var 키워드를 쓰는게 편리합니다 (다만 자바 8에서는 안됩니다)
			var merchant = merchantRepository.findById(params.getLong("merchantSno"))
					.orElseThrow(() -> new CustomException(CustomExceptionCode.ERR999, new String[] { "조회된 입점사정보가 없습니다." }))
					.toCustomMap(new String[] { "merchantLoginId", "merchantLoginPw" }); // 민감정보 제외
			
			resultMap.put("merchant", merchant);
					
		} catch (CustomException e) {
			throw new CustomException(CustomExceptionCode.ERR999, new String[] { e.getMessage() }, e);
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR511, new String[] { "입점사정보" }, e);
		}
		
		return resultMap;
	}
	
	public CustomMap getProductInfoList(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			// 페이지번호는 0부터 시작, 페이지당 10개씩, 상품일련번호 내림차순
			var pageable = PageRequest.of(params.getInt("pageNum", 0), params.getInt("rowAmountPerPage", 10), Sort.by("productSno").descending());
			var productList = productRepository.findByMerchantSno3(params.getLong("merchantSno"), pageable);
			
			// Page<Product> -> List<CustomMap> 으로 변환
			List<CustomMap> theProductList = productList.getContent().stream().map((theProduct) -> {
				CustomMap productMap = theProduct.toCustomMap(); // 공통모델로 변환
				
				productMap.put("merchant", theProduct.getMerchant().toCustomMap(new String[] { "merchantLoginId", "merchantLoginPw" }));
				productMap.put("productPrice", theProduct.getProductPrice().toCustomMap());
//				productMap.put("productOptionList", theProduct.getProductOptionList().stream().map(item -> item.toCustomMap()).toList());
				productMap.put("productOptionList", null); // 만약 필요없는 연관관계라면 N + 1 쿼리를 굳이 사용하지 않는
				
				return productMap;
			}).toList();
			
			resultMap.put("productList", theProductList);
			resultMap.put("totalPageNum", productList.getTotalPages()); // 총 페이지 개수
			resultMap.put("totalCount", productList.getTotalElements()); // 총 데이터 개수
			resultMap.put("pageNum", productList.getNumber()); // 현재 페이지 수
			resultMap.put("count", productList.getNumberOfElements()); // 현재 페이지 데이터 개수
			
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR511, new String[] { "상품정보목록" }, e);
		}
		
		return resultMap;
	}
	
	public CustomMap getProductInfoList2(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			params.put("pageNum", params.getString("pageNum", "1")); // 페이지 번호는 1부터 시작
			params.put("rowAmountPerPage", params.getString("rowAmountPerPage", "10")); // 페이지당 개수 10개
			
			List<CustomMap> productInfoList = productDao.selectProductInfoList(params);
			
			resultMap.put("productInfoList", productInfoList);
			resultMap.put("count", productInfoList.size() > 0 ? productInfoList.get(0).getString("count") : "0");
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR511, new String[] { "상품정보목록" }, e);
		}
		
		return resultMap;
	}
	
}
