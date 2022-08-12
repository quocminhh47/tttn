package com.nashtech.ecommercialwebsite.mapper;

import com.nashtech.ecommercialwebsite.data.entity.Product;
import com.nashtech.ecommercialwebsite.dto.response.ListProductHome;
import com.nashtech.ecommercialwebsite.dto.response.ProductHomeDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProductMapper {

    private final ModelMapper mapper;

    public ListProductHome getContent(Page<Product> products) {
        List<Product> productList = products.getContent();
        List<ProductHomeDto> content = productList.stream()
                .map(product -> mapper.map(product, ProductHomeDto.class))
                .collect(Collectors.toList());
        return ListProductHome.builder()
                .productContent(content)
                .pageNo(products.getNumber())
                .pageSize(products.getSize())
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages())
                .last(products.isLast())
                .build();
    }
}
