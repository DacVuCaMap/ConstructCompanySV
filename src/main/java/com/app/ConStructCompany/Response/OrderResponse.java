package com.app.ConStructCompany.Response;

import com.app.ConStructCompany.Entity.Customer;
import lombok.Data;

import java.util.Date;

@Data
public class OrderResponse {
    private Long id;
    private String representativeCustomer;
    private String representativeSeller;
    private String positionCustomer;
    private String positionSeller;
    private Double totalCost;
    private Double tax;
    private Double totalAmount;
    private Customer customer;
    private Long sellerId;
    private String contractCode;
    private String signingDate;
    private Double leftAmount;
    private Date createAt;
}
