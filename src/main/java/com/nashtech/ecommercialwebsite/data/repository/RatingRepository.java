package com.nashtech.ecommercialwebsite.data.repository;

import com.nashtech.ecommercialwebsite.data.entity.Customer;
import com.nashtech.ecommercialwebsite.data.entity.Product;
import com.nashtech.ecommercialwebsite.data.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RatingRepository extends JpaRepository<Rating, Long > {

    @Query(value = "select avg(r.rating_points) from ratings r where r.product_id = ?1", nativeQuery = true)
    Double getRatingPointsFromProduct(int id);

    @Query(value = "select r.rating_points " +
            "from ratings r" +
            " where r.customer_id = ?1 and r.product_id = ?2", nativeQuery = true)
    Integer getUserRatingPointsByProduct(int customerId, int productId);

    Rating findByProductAndCustomer(Product product, Customer customer);

   /* @Query(nativeQuery = true)
    RatingResponse getUserRatingByProduct(@Param("productId") int productId, @Param("userId") int userId);*/
}
