package com.nashtech.ecommercialwebsite.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductHomeDto {

    private  String id;

    private String name;

    private Integer price;

    private String thumbnail;
}
