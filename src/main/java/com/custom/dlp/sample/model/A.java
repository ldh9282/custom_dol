package com.custom.dlp.sample.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@ToString
public class A {

	private String name;
	@JsonManagedReference
	private B b;
	
	/**
	 * <pre>
	 * 메소드명: putB
	 * 설명: 양방향 매핑
	 * </pre>
	 * @param b
	 */
	public void putB(B b) {
		this.b = b;
		b.setA(this);
	}
	
	public static void main(String[] args) {
		A a = new A();
		a.setName("name1");
		B b = new B();
		b.setPrice(1000);
		a.putB(b);
		
		System.out.println(a); // 양방향 매핑시 toString() 순환 참조 에러
	}
}
