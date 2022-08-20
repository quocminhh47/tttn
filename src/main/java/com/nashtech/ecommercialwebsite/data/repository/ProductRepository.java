package com.nashtech.ecommercialwebsite.data.repository;

import com.nashtech.ecommercialwebsite.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "from Product p " +
            "where lower(p.name) like concat('%', :text, '%') " +
            "or lower(p.brand.name) = :brand " +
            "and p.hidden = false ")
    Page<Product> getProductByCondition(@Param("text") String text, @Param("brand") String brand, Pageable pageable);



}
