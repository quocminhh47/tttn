package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.response.RegistrationResponse;
import com.nashtech.ecommercialwebsite.dto.request.AccountRegister;
import com.nashtech.ecommercialwebsite.dto.response.TokenResponse;

public interface RegistrationService {

    TokenResponse register(AccountRegister request, String roleName);

    RegistrationResponse confirmToken(String token);

    String buildEmail(String name, String link);
}
