package com.app.ConStructCompany.Request.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentDTO {

    private Long orderId;
    private Double price;
    private Date day;
    private Long id;

}
