package com.nashtech.ecommercialwebsite.data.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(name = "noidung")
    private String message;

    @Column(name = "cmt_time")
    private LocalDateTime cmtTime;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", cmtTime=" + cmtTime +
                ", customer=" + customer +
                '}';
    }
}
