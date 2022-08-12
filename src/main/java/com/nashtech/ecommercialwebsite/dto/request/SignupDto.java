package com.nashtech.ecommercialwebsite.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Phone is required")
    private String phone;

    @NotEmpty(message = "Address is required")
    private String address;

    @NotEmpty(message = "Date of birth is required")
    private String dob;

    @NotNull(message = "gender is required")
    private Boolean gender;

}
