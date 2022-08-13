package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.data.entity.Account;

public interface AuthenticationFacadeService {
    String getCurentUsername();
    Account getAccount();
}
