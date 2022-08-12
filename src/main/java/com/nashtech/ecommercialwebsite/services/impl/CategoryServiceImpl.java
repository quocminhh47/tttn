package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Category;
import com.nashtech.ecommercialwebsite.data.repository.CategoryRepo;
import com.nashtech.ecommercialwebsite.dto.response.CategoryDto;
import com.nashtech.ecommercialwebsite.dto.response.CategoryResponse;
import com.nashtech.ecommercialwebsite.services.CategoryService;
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

public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper mapper;
    private final CategoryRepo categoryRepo;

    @Override
    public CategoryResponse getAllCategory(int pageNo,
                                           int pageSize,
                                           String sortBy,
                                           String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Category> categories = categoryRepo.findAll(pageable);

        return getContent(categories);
    }


    private CategoryDto mapToDto(Category category) {
        return mapper.map(category, CategoryDto.class);
    }


    private CategoryResponse getContent(Page<Category> categories) {
        List<Category> categoryList = categories.getContent();
        List<CategoryDto> content = categoryList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return CategoryResponse.builder()
                .categoryContent(content)
                .pageNo(categories.getNumber())
                .pageSize(categories.getSize())
                .totalElements(categories.getTotalElements())
                .totalPages(categories.getTotalPages())
                .last(categories.isLast())
                .build();

    }
}
