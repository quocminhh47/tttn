package com.nashtech.ecommercialwebsite.data.repository;

import com.nashtech.ecommercialwebsite.data.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepo extends JpaRepository<Staff, Integer> {
}
