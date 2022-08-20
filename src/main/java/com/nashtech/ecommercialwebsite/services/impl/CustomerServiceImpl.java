package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.Customer;
import com.nashtech.ecommercialwebsite.data.repository.AccountRepo;
import com.nashtech.ecommercialwebsite.dto.response.CustomerContentDto;
import com.nashtech.ecommercialwebsite.dto.response.ListCustomerResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleCustomerResponse;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.services.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final AccountRepo accountRepo;
    private final ModelMapper mapper;
    private final static String CUSTOMER_ROLE = "USER";

    @Override
    public ListCustomerResponse getAllCustomers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Account> accountPage =
                accountRepo.findAccountByRole_RoleNameAndIsEnabled(CUSTOMER_ROLE, true, pageable);
        return getContent(accountPage);
    }

    @Override
    public SingleCustomerResponse getCustomerByEmail(String email) {
        Account account = accountRepo.findAccountByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Account: " + email + "not found"));
        return mapper.map(account, SingleCustomerResponse.class);
    }

    public ListCustomerResponse getContent(Page<Account> accounts) {
        List<Account> accountList = accounts.getContent();
        List<CustomerContentDto> customerContent = accountList.stream()
                .map(account -> mapper.map(account, CustomerContentDto.class))
                .collect(Collectors.toList());
        return ListCustomerResponse.builder()
                .customers(customerContent)
                .pageNo(accounts.getNumber())
                .totalPages(accounts.getTotalPages())
                .totalElements(accounts.getTotalElements())
                .pageSize(accounts.getSize())
                .last(accounts.isLast())
                .build();
    }
}
