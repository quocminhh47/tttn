package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponse {

    private String email;

    private String staffFirstName;

    private String staffLastName;

    private String staffPhone;
}
