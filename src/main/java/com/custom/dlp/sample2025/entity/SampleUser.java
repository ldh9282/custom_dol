package com.custom.dlp.sample2025.entity;

import com.custom.dlp.cmmn.converter.CustomConverterS2I;
import com.custom.dlp.cmmn.model.CustomEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "SAMPLE2025", name = "TB001")
@SequenceGenerator(
	name = "TB001_SEQ", // 시퀀스 생성기의 이름 
	sequenceName = "SAMPLE2025.TB001_SEQ", // 실제 데이터베이스 시퀀스 이름
	allocationSize = 1 // 시퀀스 증가 크기
)
@Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SampleUser extends CustomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB001_SEQ")
	@Column(name = "샘플유저일련번호")
	private Long sampleUserSno;
	
	@Column(name = "샘플유저명")
	private String sampleUserName;
	
	@Column(name = "샘플유저이메일")
	private String sampleUserEmail;
	
	@Column(name = "샘플유저나이")
	@Convert(converter = CustomConverterS2I.class)
	private String sampleUserAge;
	
	@OneToOne(mappedBy = "sampleUser", cascade = CascadeType.ALL)
	@JsonManagedReference
	private SampleUserDtl sampleUserDtl;
	
	public void putSampleUserDtl(SampleUserDtl sampleUserDtl) {
		this.sampleUserDtl = sampleUserDtl;
		sampleUserDtl.setSampleUser(this);
	}
}
