package com.proyecto.thegiftcher.domain;

import java.io.Serializable;

/**
 * respuesta que se envía al usuario con el token
 *
 */
public class JwtResponse implements Serializable{

	private static final long serialVersionUID = 1818366400735996511L;
	
	private final String jwtToken;
	
	public JwtResponse(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	public String getToken() {
		return this.jwtToken;
	}
}
