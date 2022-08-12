package com.nashtech.ecommercialwebsite.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "hoa_don_dh")
@Getter @Setter
public class BillInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "price_total")
    private Integer priceTotal;

    @Column(name = "tax_number")
    private String taxNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ma_phieu_dat")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "ma_kh")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "mv_nv_duyet")
    private Staff approvedStaff;

}
