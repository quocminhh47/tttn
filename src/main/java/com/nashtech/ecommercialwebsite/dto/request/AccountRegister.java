package com.nashtech.ecommercialwebsite.dto.request;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class AccountRegister {

    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "phone is required")
    @Size(min = 10, message = "Phone at least 10 numbers")
    @Size(max = 12, message = "Phone max 12 numbers")
    private String phone;

    @NotBlank(message = "address is required")
    private String address;

    @NotBlank(message = "DOB is required")
    private String dob;

}
