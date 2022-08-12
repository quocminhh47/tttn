package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.*;
import com.nashtech.ecommercialwebsite.data.repository.*;
import com.nashtech.ecommercialwebsite.dto.request.SignupDto;
import com.nashtech.ecommercialwebsite.exceptions.InternalServerException;
import com.nashtech.ecommercialwebsite.exceptions.ResourceConfictException;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.services.AccountServices;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServicesImpl implements AccountServices {
    private final AccountRepo accountRepo;
    private final CustomerRepo customerRepo;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final StaffRepo staffRepo;

    @Override
    public void register(SignupDto registrationDto, String roleName) {
        Optional<Account> existAccount = accountRepo.findAccountByEmail(registrationDto.getEmail());
        if(existAccount.isPresent()) throw new ResourceConfictException(
                "Email "+ registrationDto.getEmail()+" has been already taken");

        Role role = roleRepository.findRolesByRoleName(roleName).orElseThrow(
                () -> new ResourceNotFoundException("Role "+ roleName+" not exist"));
        Account account = new Account();
        account.setEmail(registrationDto.getEmail());
        account.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        account.setIsEnabled(true);
        account.setRole(role);
        Account savedAccount = accountRepo.save(account);
        Date date;
        try {
            date = Date.valueOf(registrationDto.getDob());
            Date dateNow = new Date(new java.util.Date().getTime());
            if(date.after(dateNow)) throw new InternalServerException("Date cant be later today");
        } catch (Exception e) {
            throw new InternalServerException("Date is not valid");
        }
        if(savedAccount.getRole().getRoleName().equals("USER")) {
            System.out.println("user");
            Customer customer = new Customer();
            customer.setFirstName(registrationDto.getFirstName());
            customer.setLastName(registrationDto.getLastName());
            customer.setPhone(registrationDto.getPhone());
            customer.setAddress(registrationDto.getAddress());


            customer.setDob(date);
            customer.setGender(registrationDto.getGender());
            savedAccount.setCustomer(customer);
            customer.setAccount(savedAccount);
            Customer savedCustomer = customerRepo.save(customer);
//            System.out.println(savedCustomer.toString());
            Cart cart = new Cart();
            cart.setCustomer(savedCustomer);
            Cart savedCArt = cartRepository.save(cart);
//            customer.setCart(savedCArt);

        }

        if(savedAccount.getRole().getRoleName().equals("ADMIN")) {
            Staff staff = new Staff();
            staff.setAccount(savedAccount);
            staff.setFirstName(registrationDto.getFirstName());
            staff.setLastName(registrationDto.getLastName());
            staff.setPhone(registrationDto.getPhone());
            staff.setAddress(registrationDto.getAddress());
            staff.setGender(registrationDto.getGender());
            staff.setDob(date);
//            savedAccount.setStaff(staff);
            staffRepo.save(staff);

        }


    }

}
