package com.nashtech.ecommercialwebsite.services;


import com.nashtech.ecommercialwebsite.dto.request.ProductRequest;
import com.nashtech.ecommercialwebsite.dto.response.ListProductHome;
import com.nashtech.ecommercialwebsite.dto.response.ProductResponse;
import com.nashtech.ecommercialwebsite.dto.response.SingleProductResponse;


public interface ProductService {
//
//    ListProductHome getHomeProducts(int pageNo,
//                                    int pageSize,
//                                    String sortBy,
//                                    String sortDirection);
    SingleProductResponse findProductById(int id);
    SingleProductResponse getSingleProduct(int id);

    ProductResponse getAllProducts(int pageNo,
                                   int pageSize,
                                   String sortBy,
                                   String sortDirection);

    SingleProductResponse saveProduct(ProductRequest productRequest, Integer id);

    ListProductHome getProductsByCondition(int pageNo,
                                          int pageSize,
                                           String text,
                                           String brand);



}
