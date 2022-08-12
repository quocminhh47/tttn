package com.nashtech.ecommercialwebsite.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RatingId implements Serializable {

    @Column(name = "product_id")
    Integer productId;

    @Column(name = "customer_id")
    Integer customerId;
}
