package com.proyecto.thegiftcher.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedError extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public UnauthorizedError(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
