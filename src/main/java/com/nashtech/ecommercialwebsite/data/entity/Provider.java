package com.nashtech.ecommercialwebsite.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "nha_cung_cap")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private String thumbnail;
}
