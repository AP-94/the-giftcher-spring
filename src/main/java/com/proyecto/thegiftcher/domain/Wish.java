package com.proyecto.thegiftcher.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "wish")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Wish implements Serializable {

	private static final long serialVersionUID = 1205558563863494858L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "shop")
    private String shop;

    @Column(name = "online_shop")
    private String online_shop;

    @NotNull
    @Column(name = "category", nullable = false)
    private String category;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "reserved")
    private Boolean reserved;

    @Size(min = 4, max = 50)
    @Column(name = "location", length = 50)
    private String location;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToMany(mappedBy = "wishes")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Category categ;

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

    public void setNombre(String name) {
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

    public String getCategory() {
        return category;
    }

    public Wish category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public byte[] getImage() {
        return image;
    }

    public Wish image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Wish imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
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

    public LocalDate getDate() {
        return date;
    }

    public Wish date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Wish users(Set<User> users) {
        this.users = users;
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Category getCateg() {
        return categ;
    }

    public Wish categ(Category category) {
        this.categ = category;
        return this;
    }

    public void setCateg(Category category) {
        this.categ = category;
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
            ", category='" + getCategory() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", reserved='" + isReserved() + "'" +
            ", location='" + getLocation() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}