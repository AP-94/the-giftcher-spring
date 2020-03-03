package com.proyecto.thegiftcher.domain;

import java.io.Serializable;
/**
 * petición con usuario y contraseña que nos envía el usuario
 *
 */
public class JwtRequest implements Serializable{

	private static final long serialVersionUID = 5926468583005150707L;
	
	private String username;
	private String password;

	public JwtRequest()
	{
	}
	public JwtRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return this.username;
	}
	public void setUserName(String username) {
		this.username = username;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}