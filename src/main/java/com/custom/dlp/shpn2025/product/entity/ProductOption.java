package com.custom.dlp.shpn2025.product.entity;

import com.custom.dlp.cmmn.converter.CustomConverterS2BD;
import com.custom.dlp.cmmn.model.CustomEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * 클래스명: ProductOption
 * 설명: 상품옵션정보
 * </pre>
 */
@Entity
@Table(schema = "SHPN2025", name = "TB004")
@SequenceGenerator(
	name = "SHPN2025_TB004_SEQ", // 시퀀스 생성기의 이름
	sequenceName = "SHPN2025.TB004_SEQ", // 실제 데이터베이스 시퀀스 이름
	allocationSize = 1 // 시퀀스 증가 크기
)
@Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductOption extends CustomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHPN2025_TB004_SEQ")
	@Column(name = "상품옵션일련번호")
	private Long productOptionSno;
	
	@Column(name = "상품옵션명")
	private String productOptionName;
	
	@Column(name = "상품옵션추가금액")
	@Convert(converter = CustomConverterS2BD.class)
	private String productOptionAddPrice;
	
	@ManyToOne // 상품옵션과 상품은 다대일 관계
	@JoinColumn(name = "상품일련번호")
	@JsonBackReference
	private Product product;
}
