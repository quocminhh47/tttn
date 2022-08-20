package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Builder
public class ListCustomerResponse {

    List<CustomerContentDto> customers;

    private int pageNo;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean last;

}
