package com.nashtech.ecommercialwebsite.controller.user;

import com.nashtech.ecommercialwebsite.dto.response.BrandResponse;
import com.nashtech.ecommercialwebsite.dto.response.CategoryResponse;
import com.nashtech.ecommercialwebsite.services.CategoryService;
import com.nashtech.ecommercialwebsite.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/api/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(
                    value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                    int pageNo,
            @RequestParam(
                    value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
                    int pageSize,
            @RequestParam(
                    value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
                    String sortBy,
            @RequestParam(
                    value = "sortDir", defaultValue = "desc", required = false)
                    String sortDir
    ) {
        return new ResponseEntity<>(
                categoryService.getAllCategory(pageNo, pageSize, sortBy, sortDir),
                HttpStatus.OK);
    }

}
