package com.nashtech.ecommercialwebsite.services;


import com.nashtech.ecommercialwebsite.dto.request.SignupDto;

public interface AccountServices {

    void register(SignupDto signupDto, String role);
}
