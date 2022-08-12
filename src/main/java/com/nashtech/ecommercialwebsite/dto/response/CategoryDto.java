package com.nashtech.ecommercialwebsite.dto.response;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer id;
    private String name;
}
