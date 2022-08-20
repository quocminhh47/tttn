package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;

import java.util.Date;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailReponse {

    private String username;

    private int id;

    private int status;

    private Date createDate;

    private int priceTotal;
}
