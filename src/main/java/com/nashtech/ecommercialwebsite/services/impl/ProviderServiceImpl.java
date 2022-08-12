package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Brand;
import com.nashtech.ecommercialwebsite.data.entity.Provider;
import com.nashtech.ecommercialwebsite.data.repository.ProviderRepo;
import com.nashtech.ecommercialwebsite.dto.response.BrandDto;
import com.nashtech.ecommercialwebsite.dto.response.BrandResponse;
import com.nashtech.ecommercialwebsite.dto.response.ProviderDto;
import com.nashtech.ecommercialwebsite.dto.response.ProviderResponse;
import com.nashtech.ecommercialwebsite.services.ProviderService;
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
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepo providerRepo;
    private final ModelMapper mapper;

    @Override
    public ProviderResponse getAllProviders(int pageNo,
                                            int pageSize,
                                            String sortBy,
                                            String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Provider> providers = providerRepo.findAll(pageable);
        return getContent(providers);
    }

    private ProviderDto mapToDto(Provider provider) {
        return mapper.map(provider, ProviderDto.class);
    }

    private ProviderResponse getContent(Page<Provider> providers) {
        List<Provider> brandList = providers.getContent();
        List<ProviderDto> content = brandList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ProviderResponse.builder()
                .brandContent(content)
                .pageNo(providers.getNumber())
                .pageSize(providers.getSize())
                .totalElements(providers.getTotalElements())
                .totalPages(providers.getTotalPages())
                .last(providers.isLast())
                .build();

    }
}
