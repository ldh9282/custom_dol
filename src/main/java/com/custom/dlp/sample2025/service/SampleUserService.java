package com.custom.dlp.sample2025.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.custom.dlp.cmmn.exception.CustomException;
import com.custom.dlp.cmmn.exception.CustomExceptionCode;
import com.custom.dlp.cmmn.model.CustomMap;
import com.custom.dlp.sample2025.entity.SampleUser;
import com.custom.dlp.sample2025.entity.SampleUserDtl;

@Service
public class SampleUserService {

	@Autowired
	private SampleUserRepository sampleUserRepository;
	
	@Autowired
	private SampleUserDtlRepository sampleUserDtlRepository;
	
	@Transactional
	public CustomMap insertSampleUserInfo(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			// DB조회해서 가져온 엔터티는 영속성 객체 (영속성 컨텍스트)
			// 직접 엔터티를 생성한거는 비영속성 객체
			
			// 지금은 비영속성 객체
			SampleUser sampleUser = SampleUser.builder()
					.sampleUserName(params.getString("sampleUserName"))
					.sampleUserEmail(params.getString("sampleUserEmail"))
					.sampleUserAge(params.getString("sampleUserAge"))
					.build();
			SampleUserDtl sampleUserDtl = SampleUserDtl.builder()
					.sampleUserBaseAddr(params.getString("sampleUserBaseAddr"))
					.sampleUserDtlAddr(params.getString("sampleUserDtlAddr"))
					.build();
			sampleUser.putSampleUserDtl(sampleUserDtl); // 양방향 매핑
			
			// save -> id 가 null -> insert
			//		-> id 가 null 아니다 -> select -> update 혹은 insert
			
			// 지금은 id 가 null -> 시퀀스 채번 -> insert
			SampleUser theSampleUser = sampleUserRepository.save(sampleUser);
			resultMap.put("sampleUser", theSampleUser);
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR521, new String[] { "샘플유저정보" }, e);
		}
		
		return resultMap;
	}
	
	public CustomMap getSampleUserInfo(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			SampleUser sampleUser = sampleUserRepository.findById(params.getLong("sampleUserSno"))
//					.get()
					.orElseThrow(() -> new CustomException(
							CustomExceptionCode.ERR511, 
							new String[] { "샘플유저일련번호: " + params.getLong("sampleUserSno")}))
					;
			
			resultMap.put("sampleUser", sampleUser);
		} catch (CustomException e) {
			throw new CustomException(CustomExceptionCode.ERR999, new String[] { e.getMessage() }, e);
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR511, new String[] { "샘플유저정보" }, e);
		}
		
		return resultMap;
	}
	
	@Transactional
	public CustomMap updateSampleUserInfo(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			// 영속성 객체 (영속성 컨텍스트)
			SampleUser sampleUser = sampleUserRepository.findById(params.getLong("sampleUserSno"))
					.orElseThrow(() -> new CustomException(
							CustomExceptionCode.ERR511, 
							new String[] { "샘플유저일련번호: " + params.getLong("sampleUserSno")}))
					;
			// 더티 체킹이란 DB에서 조회한 엔터티의 변경 필드를 감지해서 save 없이 자동 update가 이루어짐
			// save를 명시적으로 쓸거면 필드를 모두 set 해야하지만, 더티 체킹은 변경할 필드만 set 해주면 된다.
			
			// 더티 체킹을 통한 자동 update
			sampleUser.setSampleUserName(params.getString("sampleUserName"));
			sampleUser.setSampleUserEmail(params.getString("sampleUserEmail"));
			sampleUser.setSampleUserAge(params.getString("sampleUserAge"));
			
			// 더티 체킹을 통한 자동 update
			SampleUserDtl sampleUserDtl = sampleUser.getSampleUserDtl();
			sampleUserDtl.setSampleUserBaseAddr(params.getString("sampleUserBaseAddr"));
			sampleUserDtl.setSampleUserDtlAddr(params.getString("sampleUserDtlAddr"));
			
			resultMap.put("sampleUser", sampleUser);
		} catch (CustomException e) {
			throw new CustomException(CustomExceptionCode.ERR999, new String[] { e.getMessage() }, e);
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR531, new String[] { "샘플유저정보" }, e);
		}
		
		return resultMap;
	}
	
	@Transactional
	public CustomMap deleteSampleUserInfo(CustomMap params) {
		// 마이바티스에서는 한줄짜리 insert, update, delete 할때는 안써도 되는데 
		// JPA 는 select 제외한 쿼리가 안나갑니다
		
		// 마이바티스에서는 쿼리를 즉시실행하지만
		// JPA 는 변경사항을 영속성 객체를 통해 감지만 해두고
		// @Transactional이 커밋 직전의 변경사항을 flush 통해 한꺼번에 실행을 해요
		CustomMap resultMap = new CustomMap();
		
		try {
			// 영속성 객체
			SampleUser sampleUser = sampleUserRepository.findById(params.getLong("sampleUserSno"))
					.orElseThrow(() -> new CustomException(
							CustomExceptionCode.ERR511, 
							new String[] { "샘플유저일련번호: " + params.getLong("sampleUserSno")}))
					;
			// 영속성 객체에 의해 연관된 자식 테이블 데이터도 삭제
			// delete 할때 null 허용 안됨
			sampleUserRepository.delete(sampleUser);
		} catch (CustomException e) {
			throw new CustomException(CustomExceptionCode.ERR999, new String[] { e.getMessage() }, e);
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR541, new String[] { "샘플유저정보" }, e);
		}
		
		return resultMap;
	}
	
	@Transactional
	public CustomMap updateSampleUserInfo2(CustomMap params) {
		CustomMap resultMap = new CustomMap();
		
		try {
			// 영속성 객체 (영속성 컨텍스트)
			SampleUserDtl sampleUserDtl = sampleUserDtlRepository.findById(params.getLong("sampleUserDtlSno"))
					.orElseThrow(() -> new CustomException(
							CustomExceptionCode.ERR511, 
							new String[] { "샘플유저상세일련번호: " + params.getLong("sampleUserDtlSno")}))
					;
			
			// 더티 체킹을 통한 자동 update
			SampleUser sampleUser = sampleUserDtl.getSampleUser();
			sampleUser.setSampleUserName(params.getString("sampleUserName"));
			sampleUser.setSampleUserEmail(params.getString("sampleUserEmail"));
			sampleUser.setSampleUserAge(params.getString("sampleUserAge"));
			
			// 더티 체킹을 통한 자동 update
			sampleUserDtl.setSampleUserBaseAddr(params.getString("sampleUserBaseAddr"));
			sampleUserDtl.setSampleUserDtlAddr(params.getString("sampleUserDtlAddr"));
			
			resultMap.put("sampleUser", sampleUser);
		} catch (CustomException e) {
			throw new CustomException(CustomExceptionCode.ERR999, new String[] { e.getMessage() }, e);
		} catch (Exception e) {
			throw new CustomException(CustomExceptionCode.ERR531, new String[] { "샘플유저정보" }, e);
		}
		
		return resultMap;
	}
}
