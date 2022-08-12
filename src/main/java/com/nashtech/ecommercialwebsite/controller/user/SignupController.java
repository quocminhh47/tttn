package com.nashtech.ecommercialwebsite.controller.user;


import com.nashtech.ecommercialwebsite.dto.request.SignupDto;
import com.nashtech.ecommercialwebsite.services.AccountServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v3/api/signup")
@AllArgsConstructor
public class SignupController {

    private final AccountServices accountService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerCustomer(@Valid @RequestBody SignupDto signupDto) {
        accountService.register(signupDto,"USER" );
        return "success" ;
    }

}
