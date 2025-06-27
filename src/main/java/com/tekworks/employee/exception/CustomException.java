package com.tekworks.employee.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomException extends Exception {
	
	private Integer status;
	private String message;
	
	public CustomException(String message) {
		super(message);
	}
	public CustomException(Integer status,String message) {
		this.status=status;
		this.message=message;
	}
	
	
	

}
