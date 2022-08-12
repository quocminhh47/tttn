package com.nashtech.ecommercialwebsite.dto.request;

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phone;

    private String address;

    private String dob;

}
