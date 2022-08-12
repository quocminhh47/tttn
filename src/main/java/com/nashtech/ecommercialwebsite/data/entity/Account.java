package com.nashtech.ecommercialwebsite.data.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "account")
@Getter @Setter @ToString
public class Account {
    @Id
    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Boolean isEnabled;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(mappedBy = "account", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Customer customer;

    @OneToOne(mappedBy = "account")
    private Staff staff;
}
