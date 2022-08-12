package com.nashtech.ecommercialwebsite.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "gio_hang")
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private  Integer id;

//    @JsonIgnore
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "ma_kh")
    private Customer customer;

    @JsonIgnore
    @OneToMany(mappedBy = "cart" , fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    private List<CartDetail> cartDetails;

 }
