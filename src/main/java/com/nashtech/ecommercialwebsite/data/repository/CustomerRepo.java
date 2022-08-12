package com.nashtech.ecommercialwebsite.data.repository;

import com.nashtech.ecommercialwebsite.data.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
}
