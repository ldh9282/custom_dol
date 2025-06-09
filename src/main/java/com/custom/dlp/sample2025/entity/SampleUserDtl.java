package com.custom.dlp.sample2025.entity;

import com.custom.dlp.cmmn.model.CustomEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
 * 클래스명: SampleUserDtl
 * 설명: 샘플유저상세정보
 * </pre>
 */
@Entity
@Table(schema = "SAMPLE2025", name = "TB002")
@SequenceGenerator(
	name = "TB002_SEQ", // 시퀀스 생성기의 이름 
	sequenceName = "SAMPLE2025.TB002_SEQ", // 실제 데이터베이스 시퀀스 이름
	allocationSize = 1 // 시퀀스 증가 크기
)
@Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SampleUserDtl extends CustomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB002_SEQ")
	@Column(name = "샘플유저상세일련번호")
	private Long sampleUserDtlSno;
	
	@Column(name = "샘플유저기본주소")
	private String sampleUserBaseAddr;
	
	@Column(name = "샘플유저상세주소")
	private String sampleUserDtlAddr;
	
	@OneToOne
	@JoinColumn(name = "샘플유저일련번호")
	@JsonBackReference
	private SampleUser sampleUser;
}
