package com.nashtech.ecommercialwebsite.data.repository;

import com.nashtech.ecommercialwebsite.data.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
