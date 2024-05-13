package com.app.ConStructCompany.Request.dto;

import com.app.ConStructCompany.Entity.Order;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private String representativeCustomer;
    private String representativeSeller;
    private String positionCustomer;
    private String positionSeller;
    private Double totalCost;
    private Double tax;
    private Double totalAmount;
    private Long customerId;
    private Long sellerId;
    private String contractCode;
    private String signingDate;
}
