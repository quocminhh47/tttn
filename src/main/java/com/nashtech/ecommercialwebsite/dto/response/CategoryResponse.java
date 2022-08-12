package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryResponse {

    List<CategoryDto> categoryContent;

    private int pageNo;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean last;
}
