package com.custom.dlp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.custom.dlp.cmmn.model.CustomMap;
import com.custom.dlp.sample.service.SampleDao;

@SpringBootTest
public class SampleTests {

	@Autowired
	private SampleDao sampleDao;
	
//	@Test
	void test01() {
		String sampleUserSno = sampleDao.selectSampleUserSno();
		String sampleUserDtlSno = sampleDao.selectSampleUserDtlSno();
		
		System.out.println("sampleUserSno ::: " + sampleUserSno);
		System.out.println("sampleUserDtlSno ::: " + sampleUserDtlSno);
	}
	
	@Test
	void test02() {
		CustomMap params = new CustomMap();
	    String sampleUserSno = sampleDao.selectSampleUserSno();

	    params.put("sampleUserSno", sampleUserSno);
	    params.put("sampleUserName", "이름1");
	    params.put("sampleUserEmail", "test@test.com");
	    params.put("sysCreator", "SYSTEM");

	    sampleDao.insertSampleUser(params);

	    String sampleUserDtlSno = sampleDao.selectSampleUserDtlSno();
	    params.put("sampleUserDtlSno", sampleUserDtlSno);
	    params.put("sampleUserRdNameAddr", "도로명주소1");
	    params.put("sampleUserDtlAddr", "상세주소1");
	    params.put("sampleUserBaseAddrYn", "1");

	    sampleDao.insertSampleUserDtl(params);
	}
}
