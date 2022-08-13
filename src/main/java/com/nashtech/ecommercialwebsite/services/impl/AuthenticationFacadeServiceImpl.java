package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.repository.AccountRepo;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.services.AuthenticationFacadeService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationFacadeServiceImpl implements AuthenticationFacadeService {

    private final AccountRepo accountRepo;
    @Override
    public String getCurentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    @Override
    public Account getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        return accountRepo.findById(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User %s not found", authentication.getName())));
    }
}
