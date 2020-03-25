package com.proyecto.thegiftcher.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "usertg")
public class User implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String username;

	@NotEmpty
	private String name;

	private String lastName;

	@NotEmpty
	private String mail;

	@NotEmpty
	private String password;

	@NotNull
	private Date birthday;

	private String imageName;
	
	private String imagePath;

	private String token;

	public User(@NotEmpty String username, @NotEmpty String name, String lastName, @NotEmpty String mail,
			@NotEmpty String password, @NotNull Date birthday) {
		super();
		this.username = username;
		this.name = name;
		this.lastName = lastName;
		this.mail = mail;
		this.password = password;
		this.birthday = birthday;
	}
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public User(Long id, String username, String name, String lastName, String mail, Date birthday, String token) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.lastName = lastName;
		this.mail = mail;
		this.birthday = birthday;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", name='" + name + '\'' +
				", lastName='" + lastName + '\'' +
				", mail='" + mail + '\'' +
				", password='" + password + '\'' +
				", birthday=" + birthday +
				", imageName=" + imageName +
				", imagePath" + imagePath +
				", token='" + token + '\'' +
				'}';
	}
}
