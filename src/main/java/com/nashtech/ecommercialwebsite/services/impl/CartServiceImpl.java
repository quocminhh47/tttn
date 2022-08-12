package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.Cart;
import com.nashtech.ecommercialwebsite.data.repository.AccountRepo;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private static final String USER_NOT_FOUND_MSG = "User with email %s not found";

    private final AccountRepo userRepository;

    @Override
    public Cart findCartByUsername(String username) {
        Account account = userRepository.findById(username)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
        return account.getCustomer().getCart();
    }

}
