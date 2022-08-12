package com.nashtech.ecommercialwebsite.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProviderDto {

    private Integer id;

    private String name;

    private String thumbnail;

    private String address;

    private String phone;

}
