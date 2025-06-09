package com.custom.dlp.shpn2025.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.custom.dlp.cmmn.exception.CustomException;
import com.custom.dlp.cmmn.exception.CustomExceptionCode;
import com.custom.dlp.cmmn.model.CustomMap;
import com.custom.dlp.shpn2025.merchant.service.MerchantRepository;
import com.custom.dlp.shpn2025.product.entity.Product;
import com.custom.dlp.shpn2025.product.entity.ProductOption;
import com.custom.dlp.shpn2025.product.entity.ProductPrice;

@Service
public class ProductService {

	@Autowired
	private MerchantRepository merchantRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Transactional
	public CustomMap insertProductInfo(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			var merchant = merchantRepository.findById(params.getLong("merchantSno"))
					.orElseThrow(() -> new CustomException(CustomExceptionCode.ERR999, new String[] { "조회된 입점사정보가 없습니다." }));
			
			Product product = Product.builder()
					.merchant(merchant) // 상품 -> 입점사 단방향 매핑
					.productName(params.getString("productName"))
					.build();
			
			ProductPrice productPrice = ProductPrice.builder()
					.productPrice(params.getString("productPrice"))
					.build();
			
			product.putProductPrice(productPrice); // 상품:상품가격 1:1 양방향 매핑
			
			List<CustomMap> productOptionList = params.getCustomMapList("productOptionList");
			
			for (CustomMap item : productOptionList) {
				ProductOption productOption = ProductOption.builder()
						.productOptionName(item.getString("productOptionName"))
						.productOptionAddPrice(item.getString("productOptionAddPrice"))
						.build();
				
				product.addProductOption(productOption); // 상품:상품옵션 1:N 양방향 매핑
			}
			
			Product theProduct = productRepository.save(product);
			CustomMap productMap = theProduct.toCustomMap(); // 공통모델로 변환
			
			productMap.put("merchant", theProduct.getMerchant().toCustomMap(new String[] { "merchantLoginId", "merchantLoginPw" }));
			productMap.put("productPrice", theProduct.getProductPrice().toCustomMap());
			productMap.put("productOptionList", theProduct.getProductOptionList().stream().map(item -> item.toCustomMap()).toList());
			
			resultMap.put("product", productMap);
			
		} catch (CustomException e) {
			throw new CustomException(CustomExceptionCode.ERR999, new String[] { e.getMessage() }, e);
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR521, new String[] { "상품기본정보" }, e);
		}
		
		return resultMap;
	}
	
	public CustomMap getProductInfo(CustomMap params) {
		// 단건매핑(@OneToOne, @ManyToOne) 은 Eager 로딩 (즉시로딩)을 하고
		// 다건매핑(@OneToMany) 은 Lazy 로딩 (지연로딩) 을 하죠
		
		// JPA 로딩전략이 기본값으로 그렇게 세팅되는 이유가 단건이니까 join key 에 인덱스가 있으면
		// 조인해서 즉시로딩해도 성능상에 문제가 없기때문입니다
		
		// 반면에 다건매핑은 건수가 많을 수가 있으니까 엑세스할때만 로딩하고 엑세스하지 않으면 쿼리를 실행하지 않습니다
		
		// 주의해야할게 엑세스라는게 값에 접근해서 직접 사용하는것뿐만 아니라
		// 엔터티를 리턴응답에 포함시키면 결국 직렬화할때 해당 필드의 게터를 사용해서 그 값에 엑세스해서 사용하기 때문에
		// 지연로딩을 해도 쿼리가 생성이 됩니다
		// 지연로딩 전략을 사용해서 쿼리를 생성시키지 않으려면 리턴응답에도 해당 연관관계가 포함되지 않는지 살펴보아야합니다
		CustomMap resultMap = new CustomMap();
		
		try {
			var product = productRepository.findById(params.getLong("productSno"))
					.orElseThrow(() -> new CustomException(CustomExceptionCode.ERR999, new String[] { "조회된 상품이 없습니다." }));
			
			CustomMap productMap = product.toCustomMap(); // 공통모델로 변환
			
			productMap.put("merchant", product.getMerchant().toCustomMap(new String[] { "merchantLoginId", "merchantLoginPw" }));
			productMap.put("productPrice", product.getProductPrice().toCustomMap());
			// 단건매핑은 Eager 로딩 (즉시로딩) 이라서 엑세스 여부와 상관없이 로딩됨
//			productMap.put("productPrice", null);
			productMap.put("productOptionList", product.getProductOptionList().stream().map(item -> item.toCustomMap()).toList());
			// 다건매핑은 Lazy 로딩이라 엑세스하지 않으면 쿼리가 생략됨
//			productMap.put("productOptionList", null);
			resultMap.put("product", productMap);
		} catch (CustomException e) {
			throw new CustomException(CustomExceptionCode.ERR999, new String[] { e.getMessage() }, e);
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR511, new String[] { "상품기본정보" }, e);
		}
		
		return resultMap;
	}
	
	@Transactional
	public CustomMap updateProductInfo(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			var product = productRepository.findById(params.getLong("productSno"))
					.orElseThrow(() -> new CustomException(CustomExceptionCode.ERR999, new String[] { "조회된 상품이 없습니다." }));
			
			product.setProductName(params.getString("productName"));
			ProductPrice productPrice = product.getProductPrice();
			productPrice.setProductPrice(params.getString("productPrice"));
			product.putProductPrice(productPrice);
			
			var productOptionList = params.getCustomMapList("productOptionList").stream().map(item -> {
				ProductOption productOption = ProductOption.builder()
						.productOptionName(item.getString("productOptionName"))
						.productOptionAddPrice(item.getString("productOptionAddPrice"))
						.build();
				
				if (!"".equals(item.getString("productOptionSno"))) {
					productOption.setProductOptionSno(item.getLong("productOptionSno"));
				}
				
				return productOption;
			}).toList();
			
			product.updateProductOptionList(productOptionList);
			// 커밋 전 더티체킹 (product 영속성 컨텍스트라서 가능)
			
			CustomMap productMap = product.toCustomMap(); // 공통모델로 변환
			
			productMap.put("merchant", product.getMerchant().toCustomMap(new String[] { "merchantLoginId", "merchantLoginPw" }));
			productMap.put("productPrice", product.getProductPrice().toCustomMap());
			productMap.put("productOptionList", product.getProductOptionList().stream().map(item -> item.toCustomMap()).toList());
			
			resultMap.put("product", productMap);
		} catch (CustomException e) {
			throw new CustomException(CustomExceptionCode.ERR999, new String[] { e.getMessage() }, e);
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR531, new String[] { "상품기본정보" }, e);
		}
		
		return resultMap;
	}
}
