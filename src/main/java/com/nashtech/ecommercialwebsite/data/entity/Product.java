package com.nashtech.ecommercialwebsite.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Builder
@Table(name = "dong_ho")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "description")
    private String description;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "hidden")
    private Boolean hidden = false;

    @Column()
    private String status;

    @Column
    private Integer guarantee;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    Set<CartDetail> cartDetails;


    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Rating> ratings;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", hidden=" + hidden +
                ", status='" + status + '\'' +
                ", guarantee=" + guarantee +
                ", brand=" + brand +
                ", provider=" + provider +
                ", category=" + category +
                '}';
    }
}
