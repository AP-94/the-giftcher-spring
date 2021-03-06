package com.proyecto.thegiftcher.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Table(name = "wish")
public class Wish implements Serializable {

	private static final long serialVersionUID = -5224805761451277006L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;
    
    //@Size(min = 3, max = 100)
    @NotEmpty
    private String name;

    private String description;

    @NotNull
    private Float price;

    private String shop;

    private String online_shop;

    @NotNull
    private Long categoryId;
    
    private String image_name;

	private String image_path;

    private Boolean reserved;

    //@Size(min = 4, max = 50)
    private String location;

    @CreationTimestamp
    private Timestamp insert_date;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Wish name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Wish description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public Wish price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getShop() {
        return shop;
    }

    public Wish shop(String shop) {
        this.shop = shop;
        return this;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getOnlineShop() {
        return online_shop;
    }

    public Wish online_shop(String online_shop) {
        this.online_shop = online_shop;
        return this;
    }

    public void setOnline_shop(String online_shop) {
        this.online_shop = online_shop;
    }

    public Long getCategory() {
        return categoryId;
    }

    public Wish categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategory(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getImageName() {
		return image_name;
	}

	public void setImageName(String imageName) {
		this.image_name = imageName;
	}
	
	 public Wish imagsName(String image) {
	        this.image_path = image;
	        return this;
	    }

    public String getImagePath() {
        return image_path;
    }

    public Wish imagePath(String image) {
        this.image_path = image;
        return this;
    }

    public void setImagePath(String image) {
        this.image_path = image;
    }
    
    public Boolean isReserved() {
        return reserved;
    }

    public Wish reserved(Boolean reserved) {
        this.reserved = reserved;
        return this;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public String getLocation() {
        return location;
    }

    public Wish location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getDate() {
        return insert_date;
    }

    public Wish date(Timestamp insert_date) {
        this.insert_date = insert_date;
        return this;
    }

    public void setDate(Timestamp insert_date) {
        this.insert_date = insert_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wish)) {
            return false;
        }
        return id != null && id.equals(((Wish) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Wish{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", shop='" + getShop() + "'" +
            ", online_shop='" + getOnlineShop() + "'" +
            ", categoryId='" + getCategory() + "'" +
            ", productImages='" + getImagePath() + "'" +
            ", imagesNames='" + getImageName() + "'" +
            ", reserved='" + isReserved() + "'" +
            ", location='" + getLocation() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}