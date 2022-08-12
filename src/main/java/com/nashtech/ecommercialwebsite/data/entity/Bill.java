package com.nashtech.ecommercialwebsite.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.lang.reflect.InvocationHandler;
import java.sql.Date;
import java.util.*;

@Entity
@Table(name = "phieu_dat")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Builder
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private  Integer id;

    //Đã duyệt hay chưa duyệt
    @Column(name = "status")
    private Integer status = 0;


    @Column(name = "create_date")
    private Date createDate;

    //Tong so tien cua don hang
    @Column(name = "price_total")
    private Integer priceTotal;

    @JsonIgnore
    @OneToMany(mappedBy = "bill", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<BillDetail> billDetails = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "delivery_staff")
    private Staff shipper;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Staff approvedStaff;

    @OneToOne(mappedBy = "bill")
    private BillInvoice billInvoice;

}
