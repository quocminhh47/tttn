package com.nashtech.ecommercialwebsite.services.impl;


import com.nashtech.ecommercialwebsite.data.entity.Brand;
import com.nashtech.ecommercialwebsite.data.entity.Category;
import com.nashtech.ecommercialwebsite.data.entity.Product;
import com.nashtech.ecommercialwebsite.data.entity.Provider;
import com.nashtech.ecommercialwebsite.data.repository.BrandRepository;
import com.nashtech.ecommercialwebsite.data.repository.CategoryRepo;
import com.nashtech.ecommercialwebsite.data.repository.ProductRepository;
import com.nashtech.ecommercialwebsite.data.repository.ProviderRepo;
import com.nashtech.ecommercialwebsite.dto.request.ProductRequest;
import com.nashtech.ecommercialwebsite.dto.response.*;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.mapper.ProductMapper;
import com.nashtech.ecommercialwebsite.services.AuthenticationFacadeService;
import com.nashtech.ecommercialwebsite.services.ProductService;
import com.nashtech.ecommercialwebsite.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ModelMapper mapper;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final AuthenticationFacadeService authenticationFacadeService;
    private final CategoryRepo categoryRepo;
    private final ProviderRepo providerRepo;
    private final RatingService ratingService;

//    @Override
//    public ListProductHome getHomeProducts(int pageNo, int pageSize, String sortBy, String sortDirection) {
//        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
//                : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//        Page<Product> productPage = productRepository.findProductByHidden(false, pageable);
//        return productMapper.getContent(productPage);
//    }

    @Override
    public SingleProductResponse findProductById(int id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product." + id + ".not.found"));
        RatingResponse ratingResponse = ratingService.getUserRatingByProduct(id);
        SingleProductResponse response = mapper.map(product, SingleProductResponse.class);
        response.setRatingResponse(ratingResponse);

        return response;
    }

    @Override
    public SingleProductResponse getSingleProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product." + id + ".not.found"));
        return mapper.map(product, SingleProductResponse.class);
    }

    @Override
    public ProductResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findAll(pageable);

        return getContent(products);
    }


    @Override
    public SingleProductResponse saveProduct(ProductRequest productRequest, Integer id) {

        Category category = categoryRepo.findById(productRequest.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category with ID: " + productRequest.getCategoryId() + " not exist"));
        Provider provider = providerRepo.findById(productRequest.getProviderId()).orElseThrow(
                () -> new ResourceNotFoundException("Provider with ID: " + productRequest.getProviderId() + " not exist"));
        Brand brand = brandRepository.findById(productRequest.getBrandId()).orElseThrow(
                () -> new ResourceNotFoundException("Brand with ID: " + productRequest.getBrandId() + " not exist"));

        if (id == null) {
            Product product = Product.builder()
                    .name(productRequest.getName())
                    .price(productRequest.getPrice())
                    .quantity(productRequest.getQuantity())
                    .description(productRequest.getDescription())
                    .thumbnail(productRequest.getThumbnail())
                    .hidden(false)
                    .status(productRequest.getStatus())
                    .guarantee(productRequest.getGuarantee())
                    .brand(brand)
                    .provider(provider)
                    .category(category)
                    .build();
            System.out.println(product.toString());
            Product savedProduct = productRepository.save(product);
            return mapper.map(savedProduct, SingleProductResponse.class);
        } else {
            productRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Product id: " + id + " not found"));

            Product product = Product.builder()
                    .id(id)
                    .name(productRequest.getName())
                    .price(productRequest.getPrice())
                    .quantity(productRequest.getQuantity())
                    .description(productRequest.getDescription())
                    .thumbnail(productRequest.getThumbnail())
                    .hidden(false)
                    .status(productRequest.getStatus())
                    .guarantee(productRequest.getGuarantee())
                    .brand(brand)
                    .provider(provider)
                    .category(category)
                    .build();

            Product savedProduct = productRepository.save(product);
            return mapper.map(savedProduct, SingleProductResponse.class);
        }

    }

    @Override
    public ListProductHome getProductsByCondition(int pageNo, int pageSize, String text, String brand) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Product> productPage = productRepository.getProductByCondition(
                text.toLowerCase(),
                brand.trim().toLowerCase(),
                pageable);
        return productMapper.getContent(productPage);
    }

    //    //convert product entity to ProductDto
    private ProductDto maptoDTO(Product product) {
        return mapper.map(product, ProductDto.class);
    }

    private Product mapToEntity(ProductRequest productRequest) {
        Product productEntity = mapper.map(productRequest, Product.class);
        Brand brand = brandRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Brand not found with id: %s", productRequest.getBrandId())));
        productEntity.setBrand(brand);
        return productEntity;
    }


    private ProductResponse getContent(Page<Product> products) {
        List<Product> listOfProducts = products.getContent();
        List<ProductDto> content = listOfProducts.stream()
                .map(this::maptoDTO)
                .collect(Collectors.toList());

        return ProductResponse.builder()
                .productContent(content)
                .currentUser(authenticationFacadeService.getCurentUsername())
                .pageNo(products.getNumber())
                .pageSize(products.getSize())
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages())
                .last(products.isLast())
                .build();
    }
}