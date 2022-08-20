package com.nashtech.ecommercialwebsite.services;


import com.nashtech.ecommercialwebsite.dto.request.SignupDto;
import com.nashtech.ecommercialwebsite.dto.response.StaffResponse;

import java.util.List;

public interface AccountServices {

    void register(SignupDto signupDto, String role);

    List<StaffResponse> getAllAccountByRole(String role);
}
