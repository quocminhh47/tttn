package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Builder
public class SingleCustomerResponse {

    private String email;
    private String customerFirstName;
    private String customerLastName;
    private String customerAddress;
    private Boolean isEnabled;
    private String customerPhone;
    private boolean customerGender;
}
