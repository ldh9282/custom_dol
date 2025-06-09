package com.custom.dlp.sample.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.custom.dlp.cmmn.model.CustomMap;

/**
 * <pre>
 * 클래스명: SampleDao
 * 설명: 샘플 Dao
 * </pre>
 */
@Repository
public class SampleDao {

	@Autowired
	private SqlSessionTemplate sst;
	
	/**
	 * <pre>
	 * 메소드명: selectSampleUserSno
	 * 설명: 샘플유저일련번호 조회
	 * </pre>
	 * @return
	 */
	public String selectSampleUserSno() {
		return sst.selectOne("Sample.selectSampleUserSno");
	}
	
	/**
	 * <pre>
	 * 메소드명: selectSampleUserDtlSno
	 * 설명: 샘플유저상세일련번호 조회
	 * </pre>
	 * @return
	 */
	public String selectSampleUserDtlSno() {
		return sst.selectOne("Sample.selectSampleUserDtlSno");
	}
	
	/**
	 * <pre>
	 * 메소드명: insertSampleUser
	 * 설명: 샘플유저기본정보 단건등록
	 * </pre>
	 * @param params
	 * @return
	 */
	public int insertSampleUser(CustomMap params) {
		return sst.insert("Sample.insertSampleUser", params);
	}
	
	/**
	 * <pre>
	 * 메소드명: insertSampleUserDtl
	 * 설명: 샘플유저상세정보 단건등록
	 * </pre>
	 * @param params
	 * @return
	 */
	public int insertSampleUserDtl(CustomMap params) {
		return sst.insert("Sample.insertSampleUserDtl", params);
	}
	
	/**
	 * <pre>
	 * 메소드명: selectSampleUser
	 * 설명: 샘플유저기본정보 단건조회
	 * </pre>
	 * @param params
	 * @return
	 */
	public CustomMap selectSampleUser(CustomMap params) {
		return sst.selectOne("Sample.selectSampleUser", params);
	}
	
	/**
	 * <pre>
	 * 메소드명: selectSampleUserDtl
	 * 설명: 샘플유저상세정보 단건조회
	 * </pre>
	 * @param params
	 * @return
	 */
	public CustomMap selectSampleUserDtl(CustomMap params) {
		return sst.selectOne("Sample.selectSampleUserDtl", params);
	}
	
	/**
	 * <pre>
	 * 메소드명: selectSampleUserInfoList
	 * 설명: 샘플유저정보 목록조회
	 * </pre>
	 * @param params
	 * @return
	 */
	public List<CustomMap> selectSampleUserInfoList(CustomMap params) {
		return sst.selectList("Sample.selectSampleUserInfoList", params);
	}
	
	/**
	 * <pre>
	 * 메소드명: updateSampleUserDtl
	 * 설명: 샘플유저상세정보 수정
	 * </pre>
	 * @param params
	 * @return
	 */
	public int updateSampleUserDtl(CustomMap params) {
		return sst.update("Sample.updateSampleUserDtl", params);
	}
	
	/**
	 * <pre>
	 * 메소드명: updateSampleUserBaseAddrYn
	 * 설명: 샘플유저기본주소여부 수정
	 * </pre>
	 * @param params
	 * @return
	 */
	public int updateSampleUserBaseAddrYn(CustomMap params) {
		return sst.update("Sample.updateSampleUserBaseAddrYn", params);
	}
	
	/**
	 * <pre>
	 * 메소드명: deleteSampleUserDtl
	 * 설명: 샘플유저상세정보 삭제
	 * </pre>
	 * @param params
	 * @return
	 */
	public int deleteSampleUserDtl(CustomMap params) {
		return sst.update("Sample.deleteSampleUserDtl", params);
	}
	
	/**
	 * <pre>
	 * 메소드명: mergeSampleUserDtl
	 * 설명: 샘플유저상세정보 수정
	 * </pre>
	 * @param params
	 * @return
	 */
	public int mergeSampleUserDtl(CustomMap params) {
		return sst.update("Sample.mergeSampleUserDtl", params);
	}
}
