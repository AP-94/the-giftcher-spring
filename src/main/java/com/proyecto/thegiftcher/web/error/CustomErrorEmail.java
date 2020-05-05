package com.proyecto.thegiftcher.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class CustomErrorEmail extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public CustomErrorEmail(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}