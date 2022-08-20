package com.nashtech.ecommercialwebsite.data.repository;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, String> {

    Optional<Account> findAccountByEmail(String s);

    Page<Account> findAccountByRole_RoleNameAndIsEnabled(String roleName, Boolean isEnabled, Pageable pageable);
}
