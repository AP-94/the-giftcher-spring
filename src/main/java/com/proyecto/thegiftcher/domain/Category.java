package com.proyecto.thegiftcher.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "category_name", length = 50, nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "categ")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Wish> wishes = new HashSet<>();

    public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCategoria() {
        return categoryName;
    }

    public Category categoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<Wish> getWishes() {
        return wishes;
    }

    public Category wishes(Set<Wish> wishes) {
        this.wishes = wishes;
        return this;
    }

    public Category addWish(Wish wish) {
        this.wishes.add(wish);
        wish.setCateg(this);
        return this;
    }

    public Category removeWish(Wish wish) {
        this.wishes.remove(wish);
        wish.setCateg(null);
        return this;
    }

    public void setWishes(Set<Wish> wishes) {
        this.wishes = wishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", categoryName='" + getCategoryName() + "'" +
            "}";
    }

	private String getCategoryName() {
		// TODO Auto-generated method stub
		return null;
	}
}