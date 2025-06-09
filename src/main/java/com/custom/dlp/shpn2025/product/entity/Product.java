package com.custom.dlp.shpn2025.product.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.custom.dlp.cmmn.model.CustomEntity;
import com.custom.dlp.shpn2025.merchant.entity.Merchant;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * 클래스명: Product
 * 설명: 상품기본정보
 * </pre>
 */
@Entity
@Table(schema = "SHPN2025", name = "TB002")
@SequenceGenerator(
	name = "SHPN2025_TB002_SEQ", // 시퀀스 생성기의 이름
	sequenceName = "SHPN2025.TB002_SEQ", // 실제 데이터베이스 시퀀스 이름
	allocationSize = 1 // 시퀀스 증가 크기
)
@Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Product extends CustomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHPN2025_TB002_SEQ")
	@Column(name = "상품일련번호")
	private Long productSno;
	
	@Column(name = "상품명")
	private String productName;
	
	@ManyToOne // Product : Merchant (N:1, 다대일)
	@JoinColumn(name = "입점사일련번호")
	private Merchant merchant;
	
	@OneToOne(mappedBy = "product", cascade = CascadeType.ALL) // Product : ProductPrice (1:1, 일대일)
	@JsonManagedReference
	private ProductPrice productPrice;
	
	// 데이터 생성기준을 생각해보면 상품과 상품옵션은 함께 등록됩니다
	// 하나의 상품에 상품옵션은 많아봐야 10개도 안될겁니다
	// 그래서 일대다지만 양방향 매핑을 해도 괜찮습니다
	// 일대다인 경우 데이터가 함께 등록되는 경우 대부분 자식 데이터가 많지 않은 경우입니다
	
	// 상품과 상품옵션은 일대다 관계 (상품과 상품옵션목록)
	// CascadeType.REMOVE 는 부모데이터 삭제될때 자식데이터도 모두 삭제
	// orphanRemoval = true 는 부모와의 매핑이 끊길때 자식데이터 삭제
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<ProductOption> productOptionList;
	
	public void putProductPrice(ProductPrice productPrice) {
		this.productPrice = productPrice;
		productPrice.setProduct(this);
	}
	
	public void addProductOption(ProductOption productOption) {
		if (productOptionList == null) {
			productOptionList = new ArrayList<>();
		}
		productOption.setProduct(this);
		productOptionList.add(productOption);
	}
	
	public void updateProductOptionList(List<ProductOption> theProductOptionList) {
		if (productOptionList == null) {
			productOptionList = new ArrayList<>();
		}
		// 기존 옵션목록의 아이템 중 수정하려는 옵션목록의 아이템과 일치하는게 없으면 삭제
		productOptionList.removeIf(item -> {
			boolean shouldRemove = theProductOptionList
					.stream().noneMatch(theItem -> Objects.equals(item.getProductOptionSno(), theItem.getProductOptionSno()));
			if (shouldRemove) {
				item.setProduct(null); // 부모와 연관관계를 끊으면 orphanRemoval = true 에 의해 DELETE 쿼리 수행
			}
			return shouldRemove; // 리스트에서도 삭제하여 양방향 일관성 유지
		});
		
		// 기존옵션 수정일때
		for (ProductOption item : productOptionList) {
			for (ProductOption theItem : theProductOptionList) {
				if (Objects.equals(item.getProductOptionSno(), theItem.getProductOptionSno())) {
					item.setProductOptionName(theItem.getProductOptionName());
					item.setProductOptionAddPrice(theItem.getProductOptionAddPrice());
				}
			}
		}
		
		// 새로 추가되는 옵션일때
		for (ProductOption theItem : theProductOptionList) {
			if (theItem.getProductOptionSno() == null) {
				this.addProductOption(theItem);
			}
		}
	}
	
}
