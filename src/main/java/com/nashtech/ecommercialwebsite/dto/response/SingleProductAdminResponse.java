package com.nashtech.ecommercialwebsite.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SingleProductAdminResponse {

    private Integer id;

    private String name;

    private Long price;

    private Integer quantity;

    private Float discount;

    private String description;

    private String brandName;

    private int brandId;

    private String thumbnail;

    private  Boolean hidden;

    private String status;

    private int guarantee;

    private String categoryName;

    private String providerName;


}
