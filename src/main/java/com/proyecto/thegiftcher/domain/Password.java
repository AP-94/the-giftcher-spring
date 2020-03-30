package com.proyecto.thegiftcher.domain;

import java.io.Serializable;

public class Password implements Serializable {

	private static final long serialVersionUID = 1L;
	
	String oldPassword;
	String newPassword;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
