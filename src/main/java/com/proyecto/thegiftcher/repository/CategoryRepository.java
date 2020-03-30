package com.proyecto.thegiftcher.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.thegiftcher.domain.Category;


@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {


    boolean existsByCategoryName(String categoryName);
}
