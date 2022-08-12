package com.nashtech.ecommercialwebsite.dto.request;

import lombok.*;


import javax.validation.constraints.*;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString @Builder
public class ProductRequest {

    @NotEmpty(message = "Name is required")
    private String name;


    @Min(value = 1, message = "Price should be positive")
    @NotNull(message = "Price cannot be blank or null")
    private Integer price;

    @Min(value = 0, message = "Price should be positive")
    @NotNull(message = "quantity cannot be blank or null")
    private Integer quantity;

    @NotEmpty(message = "Status is required")
    private String status;

    private String description;

    @NotBlank(message = "thumbnail is required")
    @NotNull(message = "thumbnail cannot be null")
    private String thumbnail;

    @Min(value = 0, message = "guaranteeTime should > 0 month")
    @NotNull(message = "guaranteeTime cannot be blank or null")
    private Integer guarantee;

    @Min(value = 1)
    @NotNull(message = "Brand is required")
    private Integer brandId;

    @Min(value = 1)
    @NotNull(message = "Provider is required")
    private Integer providerId;

    @Min(value = 1)
    @NotNull(message = "Category is required")
    private Integer categoryId;
}
