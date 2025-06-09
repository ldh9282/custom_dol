package com.custom.dlp.shpn2025.product.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.custom.dlp.cmmn.model.CustomMap;

@Repository
public class ProductDao {

	@Autowired
	private SqlSessionTemplate sst;
	
	/**
	 * <pre>
	 * 메소드명: selectProductInfoList
	 * 설명: 상품정보 목록조회
	 * </pre>
	 * @param params
	 * @return
	 */
	public List<CustomMap> selectProductInfoList(CustomMap params) {
		return sst.selectList("Product.selectProductInfoList", params);
	}
}
