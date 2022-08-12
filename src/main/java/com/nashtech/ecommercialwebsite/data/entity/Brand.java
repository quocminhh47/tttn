package com.nashtech.ecommercialwebsite.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "brands")
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @Setter(AccessLevel.NONE)
    private  Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Column
    private String thumbnail;

    @JsonIgnore
    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    Set<Product> products;

}
