package com.nashtech.ecommercialwebsite.data.entity;

import lombok.*;

import javax.persistence.*;
@Entity
@Table(name = "ratings")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder

public class Rating {

    @EmbeddedId
    private RatingId ratingId;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    @ManyToOne
    @MapsId("customerId")
    private Customer customer;

    @Column(name = "rating_points")
    private Integer ratingPoints;



}
