package com.nashtech.ecommercialwebsite.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListProviderResponse {
    private Integer id;
    private String name;
    private String address;
    private String phone;

}
