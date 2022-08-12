package com.nashtech.ecommercialwebsite.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "khach_hang")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private Date dob;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "gender")
    private Boolean gender;


    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "account_id")
    private Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "customer",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Bill> bills;

    @OneToMany(mappedBy = "customer")
    private Set<Comment> comments;

    @OneToOne(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Cart cart;

    @JsonIgnore
    @OneToMany(mappedBy = "customer",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Rating> ratings;

    @OneToMany(mappedBy = "customer")
    Set<BillInvoice> billInvoices;

}
