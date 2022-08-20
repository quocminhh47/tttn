package com.nashtech.ecommercialwebsite.controller.auth;

import com.nashtech.ecommercialwebsite.dto.response.SingleCustomerResponse;
import com.nashtech.ecommercialwebsite.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user/api")
public class CustomerController {

    private final CustomerService customerService;


    @GetMapping("/{email}")
    public SingleCustomerResponse getCustomerDetailInfo(@PathVariable("email") String email) {
        return customerService.getCustomerByEmail(email);
    }
}
