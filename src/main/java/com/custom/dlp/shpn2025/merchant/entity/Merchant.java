package com.custom.dlp.shpn2025.merchant.entity;

import com.custom.dlp.cmmn.model.CustomEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * 클래스명: Merchant
 * 설명: 입점사기본정보
 * </pre>
 */
@Entity
@Table(schema = "SHPN2025", name = "TB001")
@SequenceGenerator(
	name = "SHPN2025_TB001_SEQ", // 시퀀스 생성기의 이름
	sequenceName = "SHPN2025.TB001_SEQ", // 실제 데이터베이스 시퀀스 이름
	allocationSize = 1 // 시퀀스 증가 크기
)
@Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Merchant extends CustomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHPN2025_TB001_SEQ")
	@Column(name = "입점사일련번호")
	private Long merchantSno;
	
	@Column(name = "입점사명")
	private String merchantName;
	
	@Column(name = "입점사로그인아이디")
	private String merchantLoginId;
	
	@Column(name = "입점사로그인패스워드")
	private String merchantLoginPw;
	
	// 데이터 생성기준을 생각을 해보면 입점사가 등록되고 나중에 상품이 등록되는 경우
	// 시간이 흘러서 하나의 입점사에 몇백에서 몇만개의 상품이 등록될 수 있으므로
	// Merchant -> Product 매핑을 선택하지 않습니다.
	
	// Product -> Merchant 방향은 Product 단건당 Merchant 한건이라서 매핑해도 괜찮습니다
	// 단방향 매핑을 선택
}
