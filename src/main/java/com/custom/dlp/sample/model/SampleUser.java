package com.custom.dlp.sample.model;

import java.lang.reflect.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class SampleUser {
	
	private String sampleUserName;
	
	private String sampleUserEmail;
	
	private int sampleUserAge;
	

	@Override
	public String toString() {
		String result = "";
		
		Class<?> theClass = this.getClass();
		
		Field[] fields = theClass.getDeclaredFields();
		
		result += "\n============== " + theClass.getSimpleName() + " ==============\n\n";
		
		for (Field field : fields) {
			try {
				result += field.getName() + " : " + field.get(this) + "\n";
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		result += "\n";
		
		for (int i = 0; i < ("============== " + theClass.getSimpleName() + " ==============").length(); i++) {
			result += "=";
		}
		
		result += "\n";
		
		
		return result;
	}
	
	

}
