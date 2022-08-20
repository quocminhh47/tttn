package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Builder
public class CustomerContentDto {
    private String email;
    private String customerFirstName;
    private String customerLastName;
    private String customerPhone;
    private String customerAddress;
    private Boolean isEnabled;
}
