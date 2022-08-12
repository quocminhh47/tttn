package com.nashtech.ecommercialwebsite.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "chi_tiet_gio_hang")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDetail {

    @EmbeddedId
    CartDetailId id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @MapsId("cartID")
    @JoinColumn(name = "cart_id")
    Cart cart;

    @ManyToOne
    @MapsId("productID")
    @JoinColumn(name = "product_id")
    Product product;


}