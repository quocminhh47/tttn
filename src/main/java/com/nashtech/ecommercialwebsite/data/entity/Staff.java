package com.nashtech.ecommercialwebsite.data.entity;


import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "nhan_vien")
@NoArgsConstructor
@Getter @Setter @Builder
@AllArgsConstructor
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "approvedStaff")
    Set<Bill> approvedBills;

    @OneToMany(mappedBy = "shipper")
    Set<Bill> shipedBills;

    @OneToMany(mappedBy = "approvedStaff" )
    Set<BillInvoice> billInvoices;
}