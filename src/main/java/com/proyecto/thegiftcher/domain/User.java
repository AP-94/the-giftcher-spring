package com.proyecto.thegiftcher.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "usertg")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name="name")
	@NotEmpty
	String name;
	
	@Column(name="last_name")
	String lastName;
	
	@Column(name="mail")
	@NotEmpty
	String mail;
	
	@Column(name="password")
	@NotEmpty
	String password;
	
	@Column(name="birthday")
	@NotNull
	Date birthday;
	
	@Column(name="profile_image")
	Byte profileImage;
	
	public User(@NotEmpty String name, String lastName, @NotEmpty String mail, @NotEmpty String password,
			@NotNull Date birthday, Byte profileImage) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.mail = mail;
		this.password = password;
		this.birthday = birthday;
		this.profileImage = profileImage;
	}

	public User() {
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Date getBirthday() {
		return birthday;
	}


	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}


	public Byte getProfileImage() {
		return profileImage;
	}


	public void setProfileImage(Byte profileImage) {
		this.profileImage = profileImage;
	}
}
