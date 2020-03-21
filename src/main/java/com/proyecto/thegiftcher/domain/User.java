package com.proyecto.thegiftcher.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "usertg")
public class User implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@NotEmpty
	String username;

	@NotEmpty
	String name;

	String lastName;

	@NotEmpty
	String mail;

	@NotEmpty
	String password;

	@NotNull
	Timestamp birthday;

	@Lob
	Byte profileImage;

	String token;

	public User(@NotEmpty String username, @NotEmpty String name, String lastName, @NotEmpty String mail,
			@NotEmpty String password, @NotNull Timestamp birthday, Byte profileImage) {
		super();
		this.username = username;
		this.name = name;
		this.lastName = lastName;
		this.mail = mail;
		this.password = password;
		this.birthday = birthday;
		this.profileImage = profileImage;
	}
	
	public User(Long id, String username, String name, String lastName, String mail,Timestamp birthday, Byte profileImage, String token) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.lastName = lastName;
		this.mail = mail;
		this.birthday = birthday;
		this.profileImage = profileImage;
		this.token = token;
	}
	
	public User() {
		
	}

	public User(String username, String name, String lastName) {
		super();
		this.username = username;
		this.name = name;
		this.lastName = lastName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Timestamp getBirthday() {
		return birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public Byte getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(Byte profileImage) {
		this.profileImage = profileImage;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}
		return id != null && id.equals(((User) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + getId() + ", userName='" + getUsername() + "'" + ", name='" + getName() + "'"
				+ ", last_name='" + getLastName() + "'" + ", mail='" + getMail() + "'" + ", password='" + getPassword()
				+ "'" + ", birthday='" + getBirthday() + "'" + ", profile_image='" + getProfileImage() + "'" + ", token='" + getToken() + "'" + "}";
	}
}
