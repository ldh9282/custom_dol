package com.custom.dlp.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.custom.dlp.cmmn.exception.CustomException;
import com.custom.dlp.cmmn.exception.CustomExceptionCode;
import com.custom.dlp.cmmn.model.CustomMap;

import lombok.extern.log4j.Log4j2;

/**
 * <pre>
 * 클래스명: SampleService
 * 설명: 샘플서비스
 * </pre>
 */
@Service @Log4j2
public class SampleService {

	@Autowired
	private SampleDao sampleDao;
	
	@Transactional
	public CustomMap insertSampleUserInfo(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			String sampleUserSno = sampleDao.selectSampleUserSno();
			
			params.put("sampleUserSno", sampleUserSno);
			
			sampleDao.insertSampleUser(params);
			
			String sampleUserDtlSno = sampleDao.selectSampleUserDtlSno();
			
			params.put("sampleUserDtlSno", sampleUserDtlSno);
			
			sampleDao.insertSampleUserDtl(params);
			
			
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR521, new String[] { "샘플유저정보 단건" }, e);
		}
		
		return resultMap;
	}
	
	public CustomMap getSampleUser(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			CustomMap sampleUser = sampleDao.selectSampleUser(params);
			
			resultMap.put("sampleUser", sampleUser);
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR511, new String[] { "샘플유저기본정보" }, e);
		}
		
		return resultMap;
	}
	
	public CustomMap getSampleUserDtl(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			CustomMap sampleUserDtl = sampleDao.selectSampleUserDtl(params);
			
			resultMap.put("sampleUserDtl", sampleUserDtl);
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR511, new String[] { "샘플유저상세정보" }, e);
		}
		
		return resultMap;
	}
	
	public CustomMap getSampleUserInfoList(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			List<CustomMap> sampleUserInfoList = sampleDao.selectSampleUserInfoList(params);
			
			resultMap.put("sampleUserInfoList", sampleUserInfoList);
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR511, new String[] { "샘플유저정보 목록" }, e);
		}
		
		return resultMap;
	}
	
	public CustomMap insertSampleUserDtl(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			
			String sampleUserDtlSno = sampleDao.selectSampleUserDtlSno();
			
			params.put("sampleUserDtlSno", sampleUserDtlSno);
			
			sampleDao.insertSampleUserDtl(params);
			
			
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR521, new String[] { "샘플유저상세정보 단건" }, e);
		}
		
		return resultMap;
	}
	
	@Transactional
	public CustomMap updateSampleUserDtl(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			if ("1".equals(params.getString("sampleUserBaseAddrYn"))) {
				CustomMap requestMap = new CustomMap();
				
				requestMap.put("sampleUserSno", params.getString("sampleUserSno"));
				requestMap.put("sampleUserBaseAddrYn", "0");
				requestMap.put("sysModifier", params.getString("sysModifier"));
				
				sampleDao.updateSampleUserBaseAddrYn(requestMap);
			}
			
			sampleDao.updateSampleUserDtl(params);
			
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR531, new String[] { "샘플유저상세정보" }, e);
		}
		
		return resultMap;
	}
	
	public CustomMap deleteSampleUserDtl(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			
			sampleDao.deleteSampleUserDtl(params);
			
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR541, new String[] { "샘플유저상세정보" }, e);
		}
		
		return resultMap;
	}
	
	public CustomMap mergeSampleUserDtl(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			// 1. 시퀀스를 채번해서 보내면 insert 를 의도하는데 데이터가 있으면 update
			// 2. 그게 아니라 시퀀스를 채번하지않고 일련번호가 화면에서 넘어오면 update 를 의도하는데 데이터가 없으면 insert
			sampleDao.mergeSampleUserDtl(params);
			
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR531, new String[] { "샘플유저상세정보" }, e);
		}
		
		return resultMap;
	}
	
	@Transactional
	public CustomMap insertSampleUserInfo2(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			
			if ("1".equals(params.getString("rollbackYn1"))) {
				if (log.isDebugEnabled()) { log.debug("rollbackYn1 ::: TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()"); }
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			
			String sampleUserSno = sampleDao.selectSampleUserSno();
			
			params.put("sampleUserSno", sampleUserSno);
			
			sampleDao.insertSampleUser(params);
			
			if ("1".equals(params.getString("rollbackYn2"))) {
				if (log.isDebugEnabled()) { log.debug("rollbackYn2 ::: TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()"); }
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			
			String sampleUserDtlSno = sampleDao.selectSampleUserDtlSno();
			
			params.put("sampleUserDtlSno", sampleUserDtlSno);
			
			sampleDao.insertSampleUserDtl(params);
			
			if ("1".equals(params.getString("rollbackYn3"))) {
				if (log.isDebugEnabled()) { log.debug("rollbackYn3 ::: TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()"); }
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			
			if ("1".equals(params.getString("rollbackYn4"))) {
				throw new CustomException(CustomExceptionCode.ERR999, new String[] { "샘플유저 등록중 오류가 발생했습니다?!" });
			}
			
			
		} catch (Exception e) {
			if (log.isDebugEnabled()) { log.debug("rollbackYn4 ::: TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()"); }
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultMap.put("errorStatus", "ERR888");
			resultMap.put("errorMsg", e.getMessage());
			
		}
		
		return resultMap;
	}
}
