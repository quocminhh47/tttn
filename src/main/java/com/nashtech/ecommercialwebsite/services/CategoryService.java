package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.response.BrandResponse;
import com.nashtech.ecommercialwebsite.dto.response.CategoryResponse;

public interface CategoryService {

    CategoryResponse getAllCategory(int pageNo,
                                    int pageSize,
                                    String sortBy,
                                    String sortDir);
}
