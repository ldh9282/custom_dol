package com.custom.dlp.cmmn.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Transient;

/**
 * <pre>
 * 클래스명: CustomEntity
 * 설명: 공통처리를 위해 상속한다
 * </pre>
 */
@MappedSuperclass
public abstract class CustomEntity {
	
	@Transient
	private String sysActor;

	@Column(name = "시스템생성자", nullable = false, updatable = false)
	private String sysCreator;
	
	@Column(name = "시스템수정자", nullable = false)
	private String sysModifier;
	
	@Column(name = "시스템생성일", nullable = false, updatable = false)
	private LocalDateTime sysCreatedAt;
	
	@Column(name = "시스템수정일", nullable = false)
	private LocalDateTime sysModifiedAt;
	
	@PrePersist
	protected void onCreate() {
		if (this.sysActor == null || "".equals(this.sysActor)) {
			this.sysCreator = "SYSTEM";
			this.sysModifier = "SYSTEM";
		} else {
			this.sysCreator = this.sysActor;
			this.sysModifier = this.sysActor;
		}
		this.sysCreatedAt = LocalDateTime.now();
		this.sysModifiedAt = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		if (this.sysActor == null || "".equals(this.sysActor)) {
			this.sysModifier = "SYSTEM";
		} else {
			this.sysModifier = this.sysActor;
		}
		this.sysModifiedAt = LocalDateTime.now();
	}

	public void setSysActor(String sysActor) {
		this.sysActor = sysActor;
	}

	public String getSysActor() {
		return sysActor;
	}

	public String getSysCreator() {
		return sysCreator;
	}

	public String getSysModifier() {
		return sysModifier;
	}

	public LocalDateTime getSysCreatedAt() {
		return sysCreatedAt;
	}

	public LocalDateTime getSysModifiedAt() {
		return sysModifiedAt;
	}
	
	@Override
	public String toString() {
		return CustomMap.objectToString(this, new String[] { "log" }, true, true);
	}
	
	public CustomMap toCustomMap() {
		return CustomMap.objectToCustomMap(this, new String[] { "log" }, true, true);
	}
	
	public CustomMap toCustomMap(String... ignoredKeys) {
		List<String> excludeList = new ArrayList<>();
		excludeList.add("log"); // 항상 제외되는 기본 필드
		
		if (ignoredKeys != null) {
			excludeList.addAll(Arrays.asList(ignoredKeys));
		}
		return CustomMap.objectToCustomMap(this, excludeList.toArray(new String[0]), true, true);
	}
}
