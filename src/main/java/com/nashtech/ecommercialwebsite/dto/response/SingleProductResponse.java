package com.nashtech.ecommercialwebsite.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class SingleProductResponse {

    private Integer id;

    private String name;

    private Long price;

    private Integer quantity;

    private Float discount;

    private String description;

    private String brandName;

    private int brandId;

    private RatingResponse ratingResponse;

    private String thumbnail;

    private  Boolean hidden;

    private String status;

    private int guarantee;

    private String categoryName;

    private String providerName;


}
