package com.custom.dlp.sample.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@ToString
public class B {

	private long price;
	@JsonBackReference
	@ToString.Exclude
	private A a;
	
}
