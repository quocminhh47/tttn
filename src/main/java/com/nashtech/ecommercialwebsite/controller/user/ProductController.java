package com.nashtech.ecommercialwebsite.controller.user;


import com.nashtech.ecommercialwebsite.dto.response.ListProductHome;
import com.nashtech.ecommercialwebsite.dto.response.ProductResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleProductResponse;
import com.nashtech.ecommercialwebsite.services.ProductService;
import com.nashtech.ecommercialwebsite.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Product Resources",
        description = "Product resources that provide access to all available products")
@RestController
@RequestMapping("/user/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    @Operation(summary = "Get products list", description = "Provides all products in pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - The product list was not found",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})
    })
    public ListProductHome getAllProducts(
            @RequestParam(
                    value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                    int pageNo,
            @RequestParam(
                    value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
                    int pageSize,
            @RequestParam(
                    value = "brand", defaultValue = "", required = true)
                    String branch,
            @RequestParam(
                    value = "text", defaultValue = "", required = false)
                    String text
    ) {

        return productService.getProductsByCondition(pageNo, pageSize, text, branch);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get product detail by ID", description = "Provides product information by single")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Successfully retrieved"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "404",
                    description = "Not found - This product was not found",
                    content = {@Content(examples = {@ExampleObject()})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})

    })
    public SingleProductResponse findProductById(@PathVariable("id") int id) {
        return productService.findProductById(id);
    }
//
//    @GetMapping("/brand/{brandName}")
//    @Operation(summary = "Filter products by brand name", description = "Provides all products by brand in pagination")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "OK - Successfully retrieved"),
//            @ApiResponse(responseCode = "400",
//                    description = "Bad Request - The request is invalid",
//                    content = {@Content(examples = {@ExampleObject()})}),
//            @ApiResponse(responseCode = "404",
//                    description = "Not found - The product list was not found",
//                    content = {@Content(examples = {@ExampleObject()})}),
//            @ApiResponse(responseCode = "500",
//                    description = "Internal Error - There were some error while processing in server",
//                    content = {@Content(examples = {@ExampleObject()})})
//
//    })
//    public ResponseEntity<ProductResponse> getAllProductsByBrand(
//            @RequestParam(
//                    value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
//                    int pageNo,
//            @RequestParam(
//                    value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
//                    int pageSize,
//            @RequestParam(
//                    value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
//                    String sortBy,
//            @RequestParam(
//                    value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
//                    String sortDir,
//            @PathVariable("brandName") String brandName) {
//
//        return new ResponseEntity<>(
//                productService.getProductsByBrandName(
//                        brandName.toUpperCase(), pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
//    }
//
//
//    @GetMapping("/gender/{gender}")
//    @Operation(summary = "Filter products by gender ", description = "Provides all products  in gender selected")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "OK - Successfully retrieved"),
//            @ApiResponse(responseCode = "400",
//                    description = "Bad Request - The request is invalid",
//                    content = {@Content(examples = {@ExampleObject()})}),
//            @ApiResponse(responseCode = "404",
//                    description = "Not found - The product list was not found",
//                    content = {@Content(examples = {@ExampleObject()})}),
//            @ApiResponse(responseCode = "500",
//                    description = "Internal Error - There were some error while processing in server",
//                    content = {@Content(examples = {@ExampleObject()})})
//
//    })
//    public ResponseEntity<ProductResponse> getAllProductsByGender(
//            @RequestParam(
//                    value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
//                    int pageNo,
//            @RequestParam(
//                    value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
//                    int pageSize,
//            @RequestParam(
//                    value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
//                    String sortBy,
//            @RequestParam(
//                    value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
//                    String sortDir,
//            @PathVariable("gender") boolean gender) {
//
//        return new ResponseEntity<>(
//                productService.getProductsByGender(gender, pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
//    }
//
//
//    @GetMapping("/filter")
//    public ProductResponse filterProduct( @RequestParam("pattern") String likePattern,
//            @RequestParam(
//                    value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
//                    int pageNo,
//            @RequestParam(
//                    value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
//                    int pageSize,
//            @RequestParam(
//                    value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
//                    String sortBy,
//            @RequestParam(
//                    value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
//                    String sortDir) {
//
//        return productService.getProductByNameOrIdContaining(likePattern, pageNo, pageSize, sortBy, sortDir);
//    }


}
