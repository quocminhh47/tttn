package com.nashtech.ecommercialwebsite.services;


import com.nashtech.ecommercialwebsite.dto.response.ListCustomerResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleCustomerResponse;

public interface CustomerService {

    ListCustomerResponse getAllCustomers(int pageNo, int pageSize);

    SingleCustomerResponse getCustomerByEmail(String email);

}
