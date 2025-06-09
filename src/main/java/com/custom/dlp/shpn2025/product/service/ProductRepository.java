package com.custom.dlp.shpn2025.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.custom.dlp.shpn2025.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	// 입점사일련번호로 상품조회 페이징 이용
	// 의미론적으로 메서드 이름을 지어서 자동으로 쿼리가 생성되는 장점이 있지만
	// 다양한 검색조건 (ex: 입점사일련번호, 상품승인, 상품카테고리, 상품유형, 상품금액, 생성일시 등) 에서
	// 실무에서는 복잡한 검색조건을 여러개 사용하려면 한계가 있음
	// 이런 방식은 메서드 이름이 검색조건에 따라 길어져서 실제로 사용하기에는 한계가 많음 (강의 용도지, 실제로는 사용하기 힘듬)
	// 그래서 페이징 처리는 네이티브 쿼리가 적합합니다
	
	// @EntityGraph 사용해서 단건매핑인 연관관계를 즉시로딩을 하도록 명시할 수 있습니다
	// 단건매핑이 지연로딩될때 쿼리가 따로 나가서 직렬화될때 N + 1 쿼리가 발생하는데
	// 즉시로딩되도록하면 단건끼리 조인을 하기때문에 N개의 쿼리가 나가는걸 방지할수 있습니다
	@EntityGraph(attributePaths = {"merchant", "productPrice"})
	Page<Product> findByMerchant_MerchantSno(Long merchantSno, Pageable pageable);
	
	// @Query 로 네이티브 쿼리와 유사하게 사용할 수 있지만 문법도 SQL에 비해 불편하고 특정 DB 전용 함수도 이용하지 못함
	// 이런 방식을 사용할바엔 네이티브 쿼리를 마이바티스로 직접사용하는게 낫습니다
	// 검색조건이 복잡하거나 목록을 조회하는 쿼리는 마이바티스가 적합합니다
	
	// 상품가격은 조인을 해서 즉시로딩하는데
	// 입점사는 조인을 안하는 지연로딩이 일어납니다
	// 입점사 조회쿼리가 N개가 발생할 것 같지만 지금 경우에는 상품이 달라도 입점사가 동일하기 때문에 캐시를 활용해서 1번만 조회합니다
	@Query(value = "SELECT p FROM Product p LEFT JOIN FETCH p.productPrice WHERE p.merchant.merchantSno = :merchantSno"
			, countQuery = "SELECT COUNT(p) FROM Product p WHERE p.merchant.merchantSno = :merchantSno")
	Page<Product> findByMerchantSno(@Param("merchantSno") Long merchantSno, Pageable pageable);
	
	// 입점사는 조인을 해서 즉시로딩하는데
	// 상품가격은 조인을 안해서 지연로딩이 일어납니다
	// 상품마다 상품가격이 다르기때문에 조회쿼리가 N개가 발생합니다
	@Query(
		    value = """
		        SELECT p 
		        FROM Product p
		        LEFT JOIN FETCH p.merchant m
		        WHERE m.merchantSno = :merchantSno
		        """,
		    countQuery = """
		        SELECT COUNT(p) 
		        FROM Product p 
		        WHERE p.merchant.merchantSno = :merchantSno
		        """
		)
	Page<Product> findByMerchantSno2(@Param("merchantSno") Long merchantSno, Pageable pageable);
	
	// 입점사와 상품가격 모두 조인을 해서 즉시로딩합니다
	// 단건매핑으로 인한 N + 1 현상이 발생하지 않습니다
	@Query(
			value = """
		        SELECT p 
		        FROM Product p
		        LEFT JOIN FETCH p.merchant m
		        LEFT JOIN FETCH p.productPrice pp
		        WHERE m.merchantSno = :merchantSno
		        """,
		        countQuery = """
		        SELECT COUNT(p) 
		        FROM Product p 
		        WHERE p.merchant.merchantSno = :merchantSno
		        """
			)
	Page<Product> findByMerchantSno3(@Param("merchantSno") Long merchantSno, Pageable pageable);
	
}
