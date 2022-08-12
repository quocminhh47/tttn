package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.response.BrandResponse;
import com.nashtech.ecommercialwebsite.dto.response.ProviderResponse;

public interface ProviderService {
    ProviderResponse getAllProviders(int pageNo,
                                     int pageSize,
                                     String sortBy,
                                     String sortDir);
}
