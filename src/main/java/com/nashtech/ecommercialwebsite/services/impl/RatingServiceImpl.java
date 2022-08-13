package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.*;
import com.nashtech.ecommercialwebsite.data.repository.AccountRepo;
import com.nashtech.ecommercialwebsite.data.repository.ProductRepository;
import com.nashtech.ecommercialwebsite.data.repository.RatingRepository;
import com.nashtech.ecommercialwebsite.dto.request.UserRatingRequest;
import com.nashtech.ecommercialwebsite.dto.response.RatingResponse;
import com.nashtech.ecommercialwebsite.dto.response.UserRatingResponse;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.exceptions.UnauthorizedException;
import com.nashtech.ecommercialwebsite.services.AuthenticationFacadeService;
import com.nashtech.ecommercialwebsite.services.RatingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    private final ProductRepository productRepository;

    private final AccountRepo accountRepo;

    private final ModelMapper mapper;

    private final AuthenticationFacadeService authenticationFacadeService;

    @Override
    public RatingResponse getUserRatingByProduct(int productId) {
        Account account = authenticationFacadeService.getAccount();
        Integer customerId = account.getCustomer().getId();
        return new RatingResponse(
               ratingRepository.getRatingPointsFromProduct(productId),
               ratingRepository.getUserRatingPointsByProduct(customerId, productId)
       );
    }

    @Override
    public UserRatingResponse rateProduct(UserRatingRequest userRatingRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // authentication valid
        if(authentication != null) {
            String currentUser = authenticationFacadeService.getCurentUsername();

            Product product = productRepository.findById(userRatingRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            String.format("Product not found with id: %s", userRatingRequest.getProductId())));

            Account account = authenticationFacadeService.getAccount();

            //retrieve rating information by userId and productId
            RatingResponse ratingResponse = getUserRatingByProduct(userRatingRequest.getProductId());

            //user's never rated this product before -> create new rating
            if(ratingResponse.getUserRatingPoints() == null) {
                log.info(String.format("FIRST TIME RATING PRODUCT OF USER  %s",currentUser));
                Rating rating = Rating.builder()
                        .ratingId(new RatingId(userRatingRequest.getProductId(), account.getCustomer().getId()))
                        .ratingPoints(userRatingRequest.getRatingPoints())
                        .product(product)
                        .customer(account.getCustomer())
                        .build();
                ratingRepository.save(rating);
                UserRatingResponse res = mapper.map(rating, UserRatingResponse.class);
                res.setMess("Rating success!!");
                return res;
            }
            //user's rated this product -> update rating
            else {
                Rating oldRating = ratingRepository.findByProductAndCustomer(product, account.getCustomer());
                //update rating points
                oldRating.setRatingPoints(userRatingRequest.getRatingPoints());
                //save new rating points
                ratingRepository.save(oldRating);
                log.info("UPDATE RATING POINTS");
                UserRatingResponse res = mapper.map(oldRating, UserRatingResponse.class);
                res.setMess("Update rating success!");
                return res;
            }
        }

        //anonymous access
        else {
            throw new UnauthorizedException("You must log in first !");
        }
    }

}
