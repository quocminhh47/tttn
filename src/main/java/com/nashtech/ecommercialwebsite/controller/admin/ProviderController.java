package com.nashtech.ecommercialwebsite.controller.admin;

import com.nashtech.ecommercialwebsite.dto.response.BrandResponse;
import com.nashtech.ecommercialwebsite.dto.response.ProviderResponse;
import com.nashtech.ecommercialwebsite.services.ProviderService;
import com.nashtech.ecommercialwebsite.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api/providers")
@AllArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @GetMapping()
    public ResponseEntity<ProviderResponse> getAllBrands(
            @RequestParam(
                    value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                    int pageNo,
            @RequestParam(
                    value = "pageSize", defaultValue = "7" , required = false)
                    int pageSize,
            @RequestParam(
                    value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
                    String sortBy,
            @RequestParam(
                    value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
                    String sortDir
    ) {
        return new ResponseEntity<>(
                providerService.getAllProviders(pageNo, pageSize, sortBy, sortDir),
                HttpStatus.OK);
    }
}
